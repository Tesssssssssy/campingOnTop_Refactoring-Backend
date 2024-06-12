package com.example.campingontop.house.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetHouseListPagingByAddrReq {
    private Integer page;
    private Integer size;
    private String address;
}
