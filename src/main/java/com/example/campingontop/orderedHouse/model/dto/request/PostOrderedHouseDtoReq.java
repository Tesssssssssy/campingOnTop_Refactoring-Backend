package com.example.campingontop.orderedHouse.model.dto.request;

import com.example.campingontop.house.model.House;
import com.example.campingontop.orders.model.Orders;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostOrderedHouseDtoReq {
    private Orders orders;
    private House house;
}
