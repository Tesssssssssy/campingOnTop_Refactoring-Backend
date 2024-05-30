package com.example.campingontop.coupon.service;

import com.example.campingontop.coupon.model.Coupon;
import com.example.campingontop.coupon.model.CouponCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponQueueService {

    private final RedisTemplate<String,Object> redisTemplate;
    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;
    private static final long LAST_INDEX = 1;
    private CouponCount couponCount;

    public void setCouponCount(Coupon coupon, Integer queue){
        this.couponCount = new CouponCount(coupon, queue);
    }

    public void addQueue(Coupon coupon){
        final String people = Thread.currentThread().getName();
        final long now = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(coupon.toString(), people, (int) now);

        log.info("대기열에 추가 - {} ({}초)", people, now);
    }

    public void getOrder(Coupon coupon){
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        Set<Object> queue = redisTemplate.opsForZSet().range(coupon.getCouponName(), start, end);

        for (Object people : queue) {
            Long rank = redisTemplate.opsForZSet().rank(coupon.getCouponName(), people);
            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", people, rank);
        }
    }

    public void publish(Coupon coupon){
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - LAST_INDEX;

        Set<Object> queue = redisTemplate.opsForZSet().range(coupon.getCouponName(), start, end);

        for (Object people : queue) {
            log.info("'{}'님의 {} 기프티콘이 발급되었습니다 ({})", people, coupon.getCouponName());
            redisTemplate.opsForZSet().remove(coupon.getCouponName(), people);
            this.couponCount.decrease();
        }
    }

    public boolean validEnd(){
        return this.couponCount != null
                ? this.couponCount.end()
                : false;
    }

    public long getSize(Coupon coupon){
        return redisTemplate.opsForZSet().size(coupon.toString());
    }
}
