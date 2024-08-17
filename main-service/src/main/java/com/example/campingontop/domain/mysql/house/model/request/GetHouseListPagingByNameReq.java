package com.example.campingontop.domain.mysql.house.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetHouseListPagingByNameReq {
    private Integer page;
    private Integer size;
    private String name;
}
