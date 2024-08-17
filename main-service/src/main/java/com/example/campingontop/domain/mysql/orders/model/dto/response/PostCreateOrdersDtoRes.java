package com.example.campingontop.domain.mysql.orders.model.dto.response;

import com.example.campingontop.domain.mysql.orders.model.OrderedHouse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostCreateOrdersDtoRes {
    private Long id;
    private LocalDate orderDate;
    private List<OrderedHouse> orderedHouseList = new ArrayList<>();
}
