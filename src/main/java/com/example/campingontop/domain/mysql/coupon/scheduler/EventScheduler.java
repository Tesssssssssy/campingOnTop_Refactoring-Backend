package com.example.campingontop.domain.mysql.coupon.scheduler;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import com.example.campingontop.domain.mysql.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
        initializeEventCouponCounts();
    }

    private void initializeEventCouponCounts() {
        // 각 이벤트별 초기 쿠폰 수량 설정
        setInitialCouponCount(Event.FREE_CAMPING, 3);
        setInitialCouponCount(Event.DISCOUNT, 10);
        setInitialCouponCount(Event.GIFT_CARD, 5);
    }

    private void setInitialCouponCount(Event event, int count) {
        couponService.setEventCount(event, count);
        log.info("이벤트 {}의 초기 쿠폰 수량이 {}으로 설정되었습니다.", event.getName(), count);
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
    @Scheduled(cron = "0 0 1 * * ?")
    public void expireOldCoupon() {
        try {
            couponService.expireOldCoupon();
            log.info("만료된 쿠폰을 파기했습니다.");
        } catch (Exception e) {
            log.error("만료된 쿠폰 파기 중 오류 발생", e);
        }
    }
}
