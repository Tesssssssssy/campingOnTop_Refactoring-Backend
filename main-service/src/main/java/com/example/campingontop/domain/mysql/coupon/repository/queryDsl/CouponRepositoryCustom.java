package com.example.campingontop.domain.mysql.coupon.repository.queryDsl;

import com.example.campingontop.domain.mysql.coupon.model.Coupon;

import java.util.List;

public interface CouponRepositoryCustom {
    List<Coupon> findExpiredCoupons();
}
