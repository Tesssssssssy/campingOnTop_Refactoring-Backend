package com.example.campingontop.review.model.response;

import com.example.campingontop.orders.model.OrderedHouse;
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
