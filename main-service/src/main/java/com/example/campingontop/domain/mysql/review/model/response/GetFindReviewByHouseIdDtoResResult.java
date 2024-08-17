package com.example.campingontop.domain.mysql.review.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetFindReviewByHouseIdDtoResResult {
    private List<GetFindReviewByHouseIdDtoRes> list;
}
