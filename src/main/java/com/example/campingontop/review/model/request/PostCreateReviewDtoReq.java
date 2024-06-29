package com.example.campingontop.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateReviewDtoReq {

    @Column(nullable = false)
    private Long ordersedHouseId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer stars;
}
