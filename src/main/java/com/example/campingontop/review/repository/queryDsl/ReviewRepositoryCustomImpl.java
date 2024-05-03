package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.house.model.QHouse;
import com.example.campingontop.orderedHouse.model.QOrderedHouse;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.orders.model.QOrders;
import com.example.campingontop.review.model.QReview;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewRepositoryCustomImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {
    private EntityManager entityManager;

    public ReviewRepositoryCustomImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        super(Review.class);
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean hasPaymentHistoryForHouse(Long userId, Long houseId) {
        QOrders orders = QOrders.orders;
        QOrderedHouse orderedHouse = QOrderedHouse.orderedHouse;
        QHouse house = QHouse.house;

        long count = queryFactory
                .selectFrom(orders)
                .innerJoin(orders.orderedHouseList, orderedHouse)
                .innerJoin(orderedHouse.house, house)
                .where(
                        orders.cart.user.id.eq(userId),
                        house.id.eq(houseId)
                )
                .fetchCount();

        return count > 0;
    }

    @Override
    public Orders checkOrdersExists(User user, PostCreateReviewDtoReq req) {

        QOrders qOrders = new QOrders("orders");

        if (req.getOrdersId() == null) {
            throw new IllegalArgumentException("ordersId는 필수 영역입니다.");
        }
        Orders orders = from(qOrders)
                .leftJoin(qOrders.cart).fetchJoin()
                .where(qOrders.id.eq(req.getOrdersId()).and(qOrders.cart.user.id.eq(user.getId())))
                .distinct()
                .fetchOne();

        return orders;
    }

    @Override
    public Optional<Review> findActiveReview(Long reviewId) {
        QReview qReview = new QReview("review");

        Optional<Review> review = Optional.ofNullable(from(qReview)
                .where(qReview.id.eq(reviewId).and(qReview.status))
                .fetchOne());

        return review;
    }

    @Override
    public Page<Review> findList(Pageable pageable) {
        QReview qReview = new QReview("review");
        QOrders qOrders  = new QOrders("orders");

        List<Review> result = from(qReview)
                .innerJoin(qReview.reviewImageList).fetchJoin()
                .join(qReview.orders, qOrders).fetchJoin()
                .where(qReview.status.eq(true))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

    @Override
    public Optional<Review> findActiveReviewForDelete(User user, Long reviewId) {
        QReview qReview = new QReview("review");
        QOrders qOrders = new QOrders("orders");

        Optional<Review> review = Optional.ofNullable(from(qReview)
                        .join(qReview.orders, qOrders).fetchJoin()
                        .where(qReview.id.eq(reviewId).and(qOrders.cart.user.id.eq(user.getId())).and(qReview.status.eq(true)))
                        .fetchOne());

        return review;
    }
}
