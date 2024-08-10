package com.example.campingontop.domain.mysql.review.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetFindReviewByHouseIdDtoRes {

    private String userNickName;

    private String reviewContent;

    private Integer stars;

    private Date updatedAt;
}
