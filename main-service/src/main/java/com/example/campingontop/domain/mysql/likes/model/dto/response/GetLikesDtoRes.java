package com.example.campingontop.domain.mysql.likes.model.dto.response;

import com.example.campingontop.domain.mysql.house.model.response.GetFindHouseDtoResForLikes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetLikesDtoRes {
    private List<GetFindHouseDtoResForLikes> likesList;

    public GetLikesDtoRes(List<GetFindHouseDtoResForLikes> likesList) {
        this.likesList = likesList;
    }
}
