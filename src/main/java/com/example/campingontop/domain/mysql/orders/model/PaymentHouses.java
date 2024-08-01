package com.example.campingontop.domain.mysql.orders.model;

import com.example.campingontop.domain.mysql.orders.model.dto.response.GetPortOneRes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHouses {
    private List<GetPortOneRes> carts;
}
