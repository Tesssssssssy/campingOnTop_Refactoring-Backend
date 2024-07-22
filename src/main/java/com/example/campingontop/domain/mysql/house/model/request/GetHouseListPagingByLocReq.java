package com.example.campingontop.domain.mysql.house.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetHouseListPagingByLocReq {
    private Integer page;
    private Integer size;
    private Double latitude;
    private Double longitude;
}
