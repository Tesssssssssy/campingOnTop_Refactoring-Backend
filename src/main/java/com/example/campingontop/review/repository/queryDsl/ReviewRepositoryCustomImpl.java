package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.review.model.QReview;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.user.model.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewRepositoryCustomImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {
    public ReviewRepositoryCustomImpl() { super(Review.class); }

    @Override
    public List<Review> findReviewByUserId(Long userId) {
        QReview qReview = new QReview("Review");

        return from(qReview)
                .leftJoin(qReview.orderedHouse).fetchJoin()
                .where(qReview.status.eq(true).and(qReview.orderedHouse.cart.user.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<Review> findReviewByHouseId(Long houseId) {
        QReview qReview = new QReview("Review");

        return from(qReview)
                .leftJoin(qReview.orderedHouse)
                .fetch();
    }

    @Override
    public List<Review> findReviewByOrderedHouseId(Long orderedHouseId) {
        QReview qReview = new QReview("Review");


        return from(qReview)
                .leftJoin(qReview.orderedHouse).fetchJoin()
                .where(qReview.status.eq(true).and(qReview.orderedHouse.id.eq(orderedHouseId)))
                .fetch();
    }
}
