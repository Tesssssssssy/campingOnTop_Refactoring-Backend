package com.example.campingontop.domain.mysql.review.repository.queryDsl;

import com.example.campingontop.domain.mysql.cart.model.QCart;
import com.example.campingontop.domain.mysql.orders.model.QOrderedHouse;
import com.example.campingontop.domain.mysql.review.model.QReview;
import com.example.campingontop.domain.mysql.review.model.Review;
import com.example.campingontop.domain.mysql.user.model.QUser;
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
        QUser qUser = QUser.user;

        return from(qReview)
                .leftJoin(qReview.orderedHouse, qOrderedHouse).fetchJoin()
                .where(qReview.status.eq(true)
                        .and(qReview.user.id.eq(userId)))
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
