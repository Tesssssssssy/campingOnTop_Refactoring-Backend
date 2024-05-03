package com.example.campingontop.cart.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchDeleteCartDtoReq {
    private Long userId;
    private Long cartId;
}
