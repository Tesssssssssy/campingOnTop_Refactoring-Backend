package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.cart.model.QCart;
import com.example.campingontop.orders.model.QOrderedHouse;
import com.example.campingontop.review.model.QReview;
import com.example.campingontop.review.model.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewRepositoryCustomImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {
    public ReviewRepositoryCustomImpl() { super(Review.class); }

    private JPAQueryFactory jpaQueryFactory;

    public ReviewRepositoryCustomImpl(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Review> findReviewByUserId(Long userId) {
        QReview qReview = new QReview("Review");
        QOrderedHouse qOrderedHouse = new QOrderedHouse("OrderedHouse");
        QCart qCart = new QCart("Cart");

        return from(qReview)
                .leftJoin(qReview.orderedHouse, qOrderedHouse).fetchJoin()
                .leftJoin(qOrderedHouse.cart, qCart).fetchJoin()
                .where(qReview.status.eq(true).and(qCart.user.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<Review> findReviewByHouseId(Long houseId) {
        QReview qReview = new QReview("Review");
        QOrderedHouse qOrderedHouse = new QOrderedHouse("OrderedHouse");
        QCart qCart = new QCart("Cart");

        return from(qReview)
                .leftJoin(qReview.orderedHouse, qOrderedHouse).fetchJoin()
                .leftJoin(qOrderedHouse.cart, qCart).fetchJoin()
                .where(qReview.status.eq(true).and(qCart.house.id.eq(houseId)))
                .fetch();
    }

    @Override
    public List<Review> findReviewByOrderedHouseId(Long orderedHouseId) {
        QReview qReview = new QReview("Review");


        return from(qReview)
                .leftJoin(qReview.orderedHouse).fetchJoin()
                .where(qReview.orderedHouse.id.eq(orderedHouseId))
                .fetch();
    }
}
