package com.example.campingontop.coupon.service;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import com.example.campingontop.domain.mysql.coupon.repository.CouponRepository;
import com.example.campingontop.domain.mysql.coupon.service.CouponService;
import com.example.campingontop.enums.Gender;
import com.example.campingontop.domain.mysql.user.model.User;
import com.example.campingontop.domain.mysql.user.repository.queryDsl.UserRepository;
import com.example.campingontop.domain.mysql.userCoupon.model.UserCoupon;
import com.example.campingontop.domain.mysql.userCoupon.repository.UserCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserCouponRepository userCouponRepository;

    @MockBean
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        redisTemplate.getConnectionFactory().getConnection().flushAll(); // 테스트 전에 Redis 초기화
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void 선착순_쿠폰_발급테스트_10명_중_3명만() throws Exception {
        final Event event = Event.FREE_CAMPING;
        final int numberOfUsers = 10;
        final int couponLimit = 3;

        // Redis에 이벤트 쿠폰 수량 설정
        couponService.setEventCount(event, couponLimit);

        // 가짜 사용자 생성 메소드 호출
        List<User> users = createMockUsers(numberOfUsers);

        // 모든 요청이 처리될 때까지 기다리기 위해 CountDownLatch 사용
        CountDownLatch enqueueLatch = new CountDownLatch(numberOfUsers);
        AtomicInteger failureCount = new AtomicInteger();
        for (User user : users) {
            when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
            mockMvc.perform(post("/coupons/request/" + event.name())
                            .with(csrf())
                            .with(SecurityMockMvcRequestPostProcessors.user(user))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> {
                        if (result.getResponse().getContentAsString().contains("쿠폰 요청 추가에 실패하였습니다.")) {
                            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
                            failureCount.incrementAndGet();
                        } else {
                            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
                        }
                        enqueueLatch.countDown();
                    });
        }

        assertTrue(enqueueLatch.await(10, TimeUnit.SECONDS), "타임아웃 내에 모든 사용자가 큐에 추가되지 못했습니다.");

        // 스케줄러가 큐를 처리할 시간을 주기 위해 대기
        Thread.sleep(5000);

        // 쿠폰을 받은 사용자 수 검증
        verify(userCouponRepository, times(couponLimit)).save(any(UserCoupon.class));

        // 실패한 요청 수 검증
        assertEquals(numberOfUsers - couponLimit, failureCount.get(), "실패한 요청 수가 예상과 다릅니다.");

        // 큐에 남아있는 사용자 수 검증
        long remainingUsersInQueue = couponService.getSize(event);
        assertEquals(0, remainingUsersInQueue, "큐에 사용자가 남아 있지 않아야 합니다.");
    }


    // 가짜 사용자 생성
    private List<User> createMockUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> User.builder()
                        .email("unique" + System.nanoTime() + "@example.com")
                        .password("qwer1234!")
                        .name("User" + System.nanoTime())
                        .nickName("Nick" + System.nanoTime())
                        .phoneNum("010-" + (int) (Math.random() * 9000 + 1000) + "-" + i)
                        .gender(Gender.M)
                        .authority("ROLE_USER")
                        .birthDay("1990-01-01")
                        .status(true)
                        .build())
                .collect(Collectors.toList());
    }
}
