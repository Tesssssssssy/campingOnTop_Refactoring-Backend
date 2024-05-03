package com.example.campingontop.review.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUpdateReviewDtoReq {
    private String title;
    private String content;
    private int rating;
}
