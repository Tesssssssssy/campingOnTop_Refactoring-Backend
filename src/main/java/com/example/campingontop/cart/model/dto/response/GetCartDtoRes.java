package com.example.campingontop.cart.model.dto.response;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class GetCartDtoRes {
    private Long id;
    private String email;
    private String nickName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Date createdAt;
    private Integer price;
    private List<GetFindHouseDtoRes> getFindHouseDtoResList;

    public static GetCartDtoRes toDto(Cart cart, GetFindHouseDtoRes findHouseDtoRes) {
        return GetCartDtoRes.builder()
                .id(cart.getId())
                .email(cart.getUser().getEmail())
                .nickName(cart.getUser().getNickName())
                .checkIn(cart.getCheckIn())
                .checkOut(cart.getCheckOut())
                .price(cart.getPrice())
                .getFindHouseDtoResList(Collections.singletonList(findHouseDtoRes))
                .build();
    }
}
