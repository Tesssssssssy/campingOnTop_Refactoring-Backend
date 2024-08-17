package com.example.campingontop.domain.mysql.coupon.repository;

import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import com.example.campingontop.domain.mysql.coupon.repository.queryDsl.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
}