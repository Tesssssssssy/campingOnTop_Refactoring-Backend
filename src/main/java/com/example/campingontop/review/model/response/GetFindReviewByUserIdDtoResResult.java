package com.example.campingontop.review.model.response;

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
