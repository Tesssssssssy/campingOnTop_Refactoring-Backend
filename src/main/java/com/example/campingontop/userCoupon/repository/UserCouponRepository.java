package com.example.campingontop.userCoupon.repository;

import com.example.campingontop.coupon.constant.Event;
import com.example.campingontop.user.model.User;
import com.example.campingontop.userCoupon.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser(User user);
    Optional<UserCoupon> findByUserIdAndCouponEvent(Long userId, Event event);
}