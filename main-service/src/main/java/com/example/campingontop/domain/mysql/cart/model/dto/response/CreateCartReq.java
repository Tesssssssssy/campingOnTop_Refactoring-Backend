package com.example.campingontop.domain.mysql.cart.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartReq {
    private Long cartId;
}
