package com.example.campingontop.likes.model.dto.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.model.response.GetFindHouseDtoResForLikes;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GetLikesDtoRes {
    private List<GetFindHouseDtoResForLikes> likesList;

    public GetLikesDtoRes(List<GetFindHouseDtoResForLikes> likesList) {
        this.likesList = likesList;
    }
}
