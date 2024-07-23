package com.example.campingontop.coupon.repository;

import com.example.campingontop.coupon.model.Coupon;
import com.example.campingontop.coupon.repository.queryDsl.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
}