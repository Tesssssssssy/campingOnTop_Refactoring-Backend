package com.example.campingontop.domain.mysql.userCoupon.repository;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import com.example.campingontop.domain.mysql.user.model.User;
import com.example.campingontop.domain.mysql.userCoupon.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser(User user);
    Optional<UserCoupon> findByUserIdAndCouponEvent(Long userId, Event event);
}