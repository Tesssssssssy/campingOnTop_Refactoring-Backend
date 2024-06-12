package com.example.campingontop.house.model.response;

import com.example.campingontop.house.model.House;
import com.example.campingontop.houseImage.model.HouseImage;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFindHouseDtoRes {
    private Long id;
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

    private Integer likeCnt;
    private List<String> filenames;

    public static GetFindHouseDtoRes toDto(House house, List<String> filenames) {
        return GetFindHouseDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .content(house.getContent())
                .price(house.getPrice())
                .address(house.getAddress())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .maxUser(house.getMaxUser())
                .hasAirConditioner(house.getHasAirConditioner())
                .hasWashingMachine(house.getHasWashingMachine())
                .hasBed(house.getHasBed())
                .hasHeater(house.getHasHeater())
                .likeCnt(house.getLikeCnt())
                .filenames(filenames)
                .build();
    }

    public static GetFindHouseDtoRes toDto(House house) {
        return GetFindHouseDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .content(house.getContent())
                .price(house.getPrice())
                .address(house.getAddress())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .maxUser(house.getMaxUser())
                .hasAirConditioner(house.getHasAirConditioner())
                .hasWashingMachine(house.getHasWashingMachine())
                .hasBed(house.getHasBed())
                .hasHeater(house.getHasHeater())
                .likeCnt(house.getLikeCnt())
                .build();
    }
}
