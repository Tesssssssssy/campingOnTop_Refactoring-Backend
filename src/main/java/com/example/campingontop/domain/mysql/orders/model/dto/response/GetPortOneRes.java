package com.example.campingontop.domain.mysql.orders.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneRes {
    private Long id;
    private Integer price;
}
