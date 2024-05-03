package com.example.campingontop.cart.model.dto.response;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostCreateCartDtoRes {
    private Long id;
    private String email;
    private String nickName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Date createdAt;
    private Integer price;
    private GetFindHouseDtoRes getFindHouseDtoRes;

    public static PostCreateCartDtoRes toDto(Cart cart, List<String> filenames) {
        return PostCreateCartDtoRes.builder()
                .id(cart.getId())
                .email(cart.getUser().getEmail())
                .nickName(cart.getUser().getNickName())
                .checkIn(cart.getCheckIn())
                .checkOut(cart.getCheckOut())
                .createdAt(cart.getCreatedAt())
                .price(cart.getPrice())
                .getFindHouseDtoRes(GetFindHouseDtoRes.builder()
                        .id(cart.getHouse().getId())
                        .name(cart.getHouse().getName())
                        .content(cart.getHouse().getContent())
                        .address(cart.getHouse().getAddress())
                        .price(cart.getHouse().getPrice())
                        .maxUser(cart.getHouse().getMaxUser())
                        .latitude(cart.getHouse().getLatitude())
                        .longitude(cart.getHouse().getLongitude())
                        .hasAirConditioner(cart.getHouse().getHasAirConditioner())
                        .hasWashingMachine(cart.getHouse().getHasWashingMachine())
                        .hasBed(cart.getHouse().getHasBed())
                        .hasHeater(cart.getHouse().getHasHeater())
                        .filenames(filenames)
                        .build())
                .build();
    }
}
