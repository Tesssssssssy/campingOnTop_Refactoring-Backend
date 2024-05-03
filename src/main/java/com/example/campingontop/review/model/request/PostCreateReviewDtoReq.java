package com.example.campingontop.review.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateReviewDtoReq {
    private String title;
    private String content;
    private Integer rating;
    private Long ordersId;
}

