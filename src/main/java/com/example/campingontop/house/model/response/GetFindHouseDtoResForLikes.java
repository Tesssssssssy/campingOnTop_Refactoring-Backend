package com.example.campingontop.house.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetFindHouseDtoResForLikes {
    private Long id;
    private Long houseId;
    private String houseName;
    private String address;
    private Integer price;
    private List<String> filenames;
}
