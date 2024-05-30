package com.example.campingontop.coupon.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCouponReq {

    private Integer userIdx;

    private String couponName;

    private Integer couponDiscountRate;

    private Boolean status;

}
