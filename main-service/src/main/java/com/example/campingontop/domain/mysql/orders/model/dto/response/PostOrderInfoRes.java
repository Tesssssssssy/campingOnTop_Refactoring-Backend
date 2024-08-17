package com.example.campingontop.domain.mysql.orders.model.dto.response;

import com.example.campingontop.domain.mysql.orders.model.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PostOrderInfoRes {
    private String impUid;
    private LocalDate localDate;

    public static PostOrderInfoRes toEntity(String impUid, GetPortOneRes getPortOneRes, Orders orders) {
        return PostOrderInfoRes.builder()
                .impUid(impUid)
                .localDate(orders.getOrderDate())
                .build();
    }
}
