package com.example.campingontop.house.model.response;

import com.example.campingontop.house.model.House;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSetHouseImgDtoRes {
    private Long id;
    private String address;
    private String name;

    public static PostSetHouseImgDtoRes toDto(House house){
        return PostSetHouseImgDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .address(house.getAddress())
                .build();
    }
}
