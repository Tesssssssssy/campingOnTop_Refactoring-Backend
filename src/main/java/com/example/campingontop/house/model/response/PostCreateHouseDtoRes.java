package com.example.campingontop.house.model.response;

import com.example.campingontop.house.model.House;
import com.example.campingontop.houseImage.model.HouseImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateHouseDtoRes {
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

    private Date createdAt;

    // private List<String> filenames;

    public static PostCreateHouseDtoRes toDto(House house) {
        return PostCreateHouseDtoRes.builder()
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
                .createdAt(house.getCreatedAt())
                .build();
    }
}
