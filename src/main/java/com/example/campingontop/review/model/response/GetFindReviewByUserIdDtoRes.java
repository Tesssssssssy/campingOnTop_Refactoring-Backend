package com.example.campingontop.review.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetFindReviewByUserIdDtoRes {

    private Long reviewId;

    private String houseName;

    private Long ordersNum;

    private String reviewContent;

    private Integer stars;

    private Date updatedAt;

}
