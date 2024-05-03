package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.house.model.House;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    boolean hasPaymentHistoryForHouse(Long userId, Long houseId);
    Orders checkOrdersExists(User user, PostCreateReviewDtoReq req);
    public Optional<Review> findActiveReview(Long reviewId);

    Page<Review> findList(Pageable pageable);

    Optional<Review> findActiveReviewForDelete(User user, Long id);
}
