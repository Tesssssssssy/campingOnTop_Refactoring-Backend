package com.example.campingontop.domain.mysql.review.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFindReviewByUserIdDtoResResult {
    private List<GetFindReviewByUserIdDtoRes> list;
}
