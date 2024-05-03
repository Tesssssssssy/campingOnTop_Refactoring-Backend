package com.example.campingontop.orders.model.dto.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PostCreateOrdersDtoRes {
    private Long id;
    private String name;
    private String email;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer price;
    private String merchantUid;
    private String paymentStatus;
    private GetFindHouseDtoRes houseDto;
}
