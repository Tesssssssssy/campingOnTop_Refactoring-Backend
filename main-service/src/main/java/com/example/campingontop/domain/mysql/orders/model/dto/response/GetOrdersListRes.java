package com.example.campingontop.domain.mysql.orders.model.dto.response;

import com.example.campingontop.domain.mysql.cart.model.Cart;
import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.orders.model.Orders;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrdersListRes {
    private Long orderId;
    private LocalDate orderDate;
    private String houseName;
    private Integer price;  // 총 결제금액 추가
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static GetOrdersListRes toEntity(Orders orders, House house, Cart cart) {
        return GetOrdersListRes.builder()
                .orderId(orders.getId())
                .orderDate(orders.getOrderDate())
                .houseName(house.getName())
                .checkIn(cart.getCheckIn())
                .checkOut(cart.getCheckOut())
                .price(orders.getPrice())
                .build();
    }
}