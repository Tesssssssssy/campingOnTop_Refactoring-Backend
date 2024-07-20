package com.example.campingontop.review.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetFindReviewByHouseIdDtoResResult {
    private List<GetFindReviewByHouseIdDtoRes> list;
}
