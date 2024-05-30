package com.example.campingontop.coupon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CouponCount {
    private Coupon coupon;
    private Integer limit;

    private static final Integer end = 0;

    public CouponCount(Coupon coupon, Integer limit){
        this.coupon = coupon;
        this.limit = limit;
    }

    public synchronized void decrease(){
        this.limit--;
    }

    public boolean end(){
        return this.limit == end;
    }
}
