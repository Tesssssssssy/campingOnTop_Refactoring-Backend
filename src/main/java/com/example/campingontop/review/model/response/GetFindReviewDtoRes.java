package com.example.campingontop.review.model.response;

import com.example.campingontop.review.model.Review;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFindReviewDtoRes {
    private Long id;
    private String title;
    private String content;
    private int rating;
    private int ratingAvg;
    private String houseName;
    private Date createdAt;
    private Date updatedAt;
    private List<String> imageUrls;

    public static GetFindReviewDtoRes toDto(Review review, List<String> filenames, Integer ratingAvg){
        return GetFindReviewDtoRes.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .ratingAvg(ratingAvg)
                .houseName(review.getOrders().getCart().getHouse().getName())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .imageUrls(filenames)
                .build();
    }

    public static GetFindReviewDtoRes toDto(Review review, List<String> filenames){
        return GetFindReviewDtoRes.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .houseName(review.getOrders().getCart().getHouse().getName())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .imageUrls(filenames)
                .build();
    }
}
