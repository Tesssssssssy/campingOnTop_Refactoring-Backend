package com.example.campingontop.review.model.request;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateReviewDtoReq {

    private Long orderedHouseId;
    private String content;
    private Integer stars;
}
