package com.example.campingontop.house.model.response;

import com.example.campingontop.house.model.House;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHouseLikeDtoRes {
    private Long id;
    private String name;
    private Integer likeCnt;

    public static GetHouseLikeDtoRes toDto(House house) {
        return GetHouseLikeDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .likeCnt(house.getLikeCnt())
                .build();
    }
}
