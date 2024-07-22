package com.example.campingontop.domain.mysql.coupon.scheduler;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import com.example.campingontop.domain.mysql.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final CouponService couponService;

    // 애플리케이션 시작 시 쿠폰 수량 초기화
    @PostConstruct
    private void init() {
        Event event = Event.FREE_CAMPING;
        int initialCouponCount = 3; // 초기 쿠폰 수량 설정 (예: 3개)
        couponService.setEventCount(event, initialCouponCount);
        log.info("이벤트 {}의 초기 쿠폰 수량이 {}으로 설정되었습니다.", event, initialCouponCount);
    }

    // 요청이 들어올 때 호출되는 메서드
    public void processQueue(Event event) {
        // 쿠폰이 남아 있는지 확인한 후 발행 시도
        if (couponService.getRemainingEventCount(event) > 0) {
            log.info("이벤트 처리 중: {}", event);
            couponService.publish(event);
        } else {
            log.info("===== 선착순 이벤트가 종료되었습니다: {} =====", event);
        }
    }
}
