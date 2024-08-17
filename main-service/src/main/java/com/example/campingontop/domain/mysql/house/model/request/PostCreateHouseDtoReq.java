package com.example.campingontop.domain.mysql.house.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateHouseDtoReq {
    private String name;
    private String content;
    private Integer price;

    private String address;
    private Double latitude;
    private Double longitude;

    private Integer maxUser;

    private Boolean hasAirConditioner;
    private Boolean hasWashingMachine;
    private Boolean hasBed;
    private Boolean hasHeater;
}
