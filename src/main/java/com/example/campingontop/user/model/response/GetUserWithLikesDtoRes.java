package com.example.campingontop.user.model.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
