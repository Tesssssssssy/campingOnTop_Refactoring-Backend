package com.example.campingontop.likes.model.dto.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GetLikesDtoRes {
    /*
    private Long id;
    private String email;
    private String name;
    private List<GetFindHouseDtoRes> houseDtoResList;
    */
    private Map<Long, GetFindHouseDtoRes> likesList;

    public GetLikesDtoRes(Map<Long, GetFindHouseDtoRes> likesInfoMap) {
        this.likesList = likesInfoMap;
    }
}
