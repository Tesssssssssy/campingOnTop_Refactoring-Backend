package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.review.model.Review;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findReviewByUserId(Long userId);

    List<Review> findReviewByHouseId(Long houseId);

    List<Review> findReviewByOrderedHouseId(Long orderId);
}
