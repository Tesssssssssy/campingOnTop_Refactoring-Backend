package com.example.campingontop.domain.mysql.coupon.repository;

import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}