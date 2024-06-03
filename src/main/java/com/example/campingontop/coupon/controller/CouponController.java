package com.example.campingontop.coupon.controller;

import com.example.campingontop.coupon.constant.Event;
import com.example.campingontop.coupon.model.Coupon;
import com.example.campingontop.coupon.model.dto.GetCouponRes;
import com.example.campingontop.coupon.service.CouponService;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/request/{eventId}")
    public ResponseEntity<String> requestCoupon(@PathVariable("eventId") String eventId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 인증되지 않았습니다.");
        }

        Event event = Event.valueOf(eventId);
        // 사용자가 이미 해당 이벤트의 쿠폰을 받았는지 확인
        if (!couponService.canIssueCoupon(user, event)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 해당 이벤트의 쿠폰을 받았습니다.");
        }

        // 쿠폰 발급 큐에 사용자 추가 시도
        boolean addedToQueue = couponService.addQueue(event, user);
        if (!addedToQueue) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠폰 요청 추가에 실패하였습니다.");
        }

        // 큐에 추가 후 실제 쿠폰 발급 처리
        couponService.processQueue(event);

        return ResponseEntity.ok("쿠폰 요청이 대기열에 추가되었습니다.");
    }

    @GetMapping("/my")
    public ResponseEntity<List<GetCouponRes>> myCoupons(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<GetCouponRes> coupons = couponService.getUserCoupons(user);
        if (coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(coupons);
    }
}
