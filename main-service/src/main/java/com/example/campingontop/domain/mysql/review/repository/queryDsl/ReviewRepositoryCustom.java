package com.example.campingontop.domain.mysql.review.repository.queryDsl;

import com.example.campingontop.domain.mysql.review.model.Review;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findReviewByUserId(Long userId);

    List<Review> findReviewByHouseId(Long houseId);

    List<Review> findReviewByOrderedHouseId(Long orderId);
}
