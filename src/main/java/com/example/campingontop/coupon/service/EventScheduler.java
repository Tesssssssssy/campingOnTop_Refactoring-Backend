package com.example.campingontop.coupon.service;

import com.example.campingontop.coupon.model.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final CouponQueueService couponQueueService;

    private final Coupon coupon;

    @Scheduled(fixedDelay = 1000)
    private void couponEventScheduler(){
        if(couponQueueService.validEnd()){
            log.info("===== 선착순 이벤트가 종료되었습니다. =====");
            return;
        }
        couponQueueService.publish(coupon);
        couponQueueService.getOrder(coupon);
    }
}
