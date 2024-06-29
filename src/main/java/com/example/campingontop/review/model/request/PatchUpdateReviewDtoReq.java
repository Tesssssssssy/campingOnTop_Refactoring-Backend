package com.example.campingontop.review.model.request;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUpdateReviewDtoReq {

    @Column(nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer stars;

}
