package com.example.campingontop.coupon.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCouponRes {
    private Long id;
    private String eventName;
    private Integer price;
    private String issuedAt;
}
