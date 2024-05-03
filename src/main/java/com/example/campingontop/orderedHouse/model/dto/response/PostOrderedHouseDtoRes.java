package com.example.campingontop.orderedHouse.model.dto.response;

import com.example.campingontop.house.model.House;
import com.example.campingontop.orders.model.Orders;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostOrderedHouseDtoRes {
    private Long id;
    private Orders orders;
    private House house;
}
