package com.example.campingontop.review.model.response;

import com.example.campingontop.review.model.Review;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUpdateReviewDtoRes {
    private Long id;
    private String title;
    private String content;
    private int rating;
    private String houseName;

    public static PatchUpdateReviewDtoRes toDto(Review review){
        return PatchUpdateReviewDtoRes.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .houseName(review.getOrders().getCart().getHouse().getName())
                .build();
    }
}
