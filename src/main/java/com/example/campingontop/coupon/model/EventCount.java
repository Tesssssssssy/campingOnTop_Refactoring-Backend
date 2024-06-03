package com.example.campingontop.coupon.model;

import com.example.campingontop.coupon.constant.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCount {
    private final Event event;
    private int remainingCount;

    public EventCount(Event event, int count) {
        this.event = event;
        this.remainingCount = count;
    }

    public synchronized void decrease() {
        if (remainingCount > 0) {
            remainingCount--;
        }
    }

    public synchronized boolean end() {
        return remainingCount <= 0;
    }

    public synchronized int getRemainingCount() {
        return remainingCount;
    }
}
