package com.example.campingontop.domain.mysql.review.model.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUpdateReviewDtoRes {

    private String content;

    private Integer stars;

    private Date createdAt;

    private Date updatedAt;

    private Long orderedHouseId;
}
