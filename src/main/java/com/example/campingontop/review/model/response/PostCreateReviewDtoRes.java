package com.example.campingontop.review.model.response;

import com.example.campingontop.review.model.Review;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateReviewDtoRes {
    private String title;
    private String content;
    private int rating;
    private Date createdAt;

    public static PostCreateReviewDtoRes toDto(Review review) {
        return PostCreateReviewDtoRes.builder()
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

