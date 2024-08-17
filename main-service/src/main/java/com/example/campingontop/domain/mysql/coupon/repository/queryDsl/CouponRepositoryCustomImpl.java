package com.example.campingontop.domain.mysql.coupon.repository.queryDsl;

import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import com.example.campingontop.domain.mysql.coupon.model.QCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Date;
import java.util.List;

public class CouponRepositoryCustomImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {
    public CouponRepositoryCustomImpl(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public CouponRepositoryCustomImpl() {super(Coupon.class);}

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Coupon> findExpiredCoupons() {
        QCoupon qCoupon = new QCoupon("coupon");

        List<Coupon> result = from(qCoupon)
                .where(qCoupon.status.eq(true).and(qCoupon.expiryTime.before(new Date())))
                .fetch();

        return result;
    }
}
