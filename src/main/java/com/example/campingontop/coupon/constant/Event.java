package com.example.campingontop.coupon.constant;

import lombok.Getter;

@Getter
public enum Event {
    FREE_CAMPING("무료숙박권", 10000),
    DISCOUNT("할인쿠폰", 5000),
    GIFT_CARD("기프트카드", 20000);

    private final String name;
    private final int price;

    Event(String name, int price) {
        this.name = name;
        this.price = price;
    }
}