package com.example.campingontop.domain.mysql.coupon.service;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import com.example.campingontop.domain.mysql.coupon.model.dto.GetCouponRes;
import com.example.campingontop.domain.mysql.coupon.repository.CouponRepository;
import com.example.campingontop.domain.mysql.user.model.User;
import com.example.campingontop.domain.mysql.user.repository.queryDsl.UserRepository;
import com.example.campingontop.domain.mysql.userCoupon.model.UserCoupon;
import com.example.campingontop.domain.mysql.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    // 이벤트 쿠폰 수량 설정
    public void setEventCount(Event event, int count) {
        redisTemplate.opsForValue().set(event.name() + "_COUNT", String.valueOf(count));
    }

    // 남은 이벤트 쿠폰 수량 가져오기
    public int getRemainingEventCount(Event event) {
        String countStr = redisTemplate.opsForValue().get(event.name() + "_COUNT");
        if (countStr != null) {
            try {
                return Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                log.error("Redis에서 카운트 값을 파싱하지 못했습니다", e);
            }
        }
        return 0;
    }

    // 이벤트 쿠폰 수량 감소
    public void decrementEventCount(Event event) {
        redisTemplate.opsForValue().decrement(event.name() + "_COUNT");
    }

    // 큐에 사용자 추가
    public boolean addQueue(Event event, User user) {
        if (user == null) {
            log.error("사용자가 null입니다");
            throw new IllegalArgumentException("사용자는 null일 수 없습니다");
        }
        if (event == null) {
            log.error("이벤트가 null입니다");
            throw new IllegalArgumentException("이벤트는 null일 수 없습니다");
        }
        if (redisTemplate == null) {
            log.error("RedisTemplate이 null입니다");
            throw new IllegalStateException("RedisTemplate은 null일 수 없습니다");
        }

        if (getRemainingEventCount(event) <= 0) {
            log.error("이벤트에 대한 쿠폰이 더 이상 남아 있지 않습니다: {}", event);
            return false;
        }

        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(event.toString(), user.getEmail(), now);
        log.info("큐에 추가됨 - {} at {}ms", user.getName(), now);

        // 큐에 추가된 후 바로 큐를 처리
        processQueue(event);

        return true;
    }

    // 큐를 처리하는 메서드
    public void processQueue(Event event) {
        // 쿠폰이 남아 있는지 확인한 후 발행 시도
        if (getRemainingEventCount(event) <= 0) {
            log.info("===== 선착순 이벤트가 종료되었습니다: {} =====", event);
            return;
        }

        log.info("이벤트 처리 중: {}", event);
        publish(event);
    }

    // 큐에 있는 사용자들에게 쿠폰 발행
    public void publish(Event event) {
        List<User> users = getUsersFromQueue(event);
        for (User user : users) {
            if (getRemainingEventCount(event) <= 0) {
                log.info("쿠폰이 모두 소진되었습니다.");
                break;
            }
            issueCoupon(event, user);
            decrementEventCount(event);
            redisTemplate.opsForZSet().remove(event.toString(), user.getEmail());
        }
    }

    // 해당 이벤트의 쿠폰을 이미 받았는지 확인
    public boolean canIssueCoupon(User user, Event event) {
        return userCouponRepository.findByUserIdAndCouponEvent(user.getId(), event).isEmpty();
    }

    // 큐에 남아있는 사용자 수 가져오기
    public long getSize(Event event) {
        return redisTemplate.opsForZSet().size(event.toString());
    }

    // 사용자에게 쿠폰 발행
    public void issueCoupon(Event event, User user) {
        Coupon coupon = couponRepository.save(Coupon.builder()
                .event(event)
                .price(10000)
                .build());
        userCouponRepository.save(UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .isUsed(false)
                .build());
        log.info("'{}'에게 {} 쿠폰이 발급되었습니다", user.getName(), event.name());
    }

    // 큐에 있는 사용자 목록 가져오기
    private List<User> getUsersFromQueue(Event event) {
        Set<String> emails = redisTemplate.opsForZSet().range(event.toString(), 0, -1);
        List<User> users = new ArrayList<>();
        if (emails != null) {
            for (String email : emails) {
                userRepository.findByEmail(email).ifPresent(users::add);
            }
        }
        return users;
    }

    // 쿠폰 발급 내역 조회
    public List<GetCouponRes> getUserCoupons(User user) {
        return userCouponRepository.findByUser(user).stream()
                .filter(userCoupon -> !userCoupon.isUsed())  // Only include unused coupons
                .map(userCoupon -> GetCouponRes.builder()
                        .id(userCoupon.getCoupon().getId())
                        .eventName(userCoupon.getCoupon().getEvent().name())
                        .price(userCoupon.getCoupon().getPrice())
                        .issuedAt(userCoupon.getCoupon().getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
