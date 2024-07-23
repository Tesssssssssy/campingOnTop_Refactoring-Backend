package com.example.campingontop.coupon.repository.queryDsl;

import com.example.campingontop.coupon.model.Coupon;

import java.util.List;

public interface CouponRepositoryCustom {
    List<Coupon> findExpiredCoupons();
}
