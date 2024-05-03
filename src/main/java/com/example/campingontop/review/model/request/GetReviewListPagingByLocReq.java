package com.example.campingontop.review.model.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class GetReviewListPagingByLocReq {
        private Integer page;
        private Integer size;

}
