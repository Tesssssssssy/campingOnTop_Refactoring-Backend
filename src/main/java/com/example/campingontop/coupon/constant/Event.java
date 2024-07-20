package com.example.campingontop.coupon.constant;

import lombok.Getter;

@Getter
public enum Event {
    FREE_CAMPING("무료숙박권");

    private String name;

    Event(String name) {
        this.name = name;
    }
}