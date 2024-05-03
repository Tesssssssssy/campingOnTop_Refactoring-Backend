package com.example.campingontop.house.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUpdateHouseDtoReq {
    private String name;
    private String content;
    private Integer price;

    private String address;
    private Double latitude;
    private Double longitude;

    private Integer maxUser;

    private Boolean isActive;
    private Boolean hasAirConditioner;
    private Boolean hasWashingMachine;
    private Boolean hasBed;
    private Boolean hasHeater;
}
