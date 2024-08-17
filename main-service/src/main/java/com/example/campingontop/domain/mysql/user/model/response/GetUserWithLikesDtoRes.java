package com.example.campingontop.domain.mysql.user.model.response;

import com.example.campingontop.domain.mysql.house.model.response.GetFindHouseDtoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetUserWithLikesDtoRes {
    private Long id;
    private String email;
    private String name;
    private GetFindHouseDtoRes likedHouse;
}
