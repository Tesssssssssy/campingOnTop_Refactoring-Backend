package com.example.campingontop.review.model.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateReviewDtoRes {

    private Long reviewId;

    private String content;

    private String houseName;

    private Long ordersId;

    private Integer stars;

    private Date createdAt;

    private Date updatedAt;

}
