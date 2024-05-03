package com.example.campingontop.house.repository.queryDsl;

import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.QHouse;
import com.example.campingontop.user.model.QUser;
import com.example.campingontop.user.model.User;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HouseRepositoryCustomImpl extends QuerydslRepositorySupport implements HouseRepositoryCustom {
    public HouseRepositoryCustomImpl() {
        super(House.class);
    }

    public HouseRepositoryCustomImpl(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<House> findList(Pageable pageable) {
        QHouse house = new QHouse("house");

        List<House> result = from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .where(house.status.eq(true))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }


    @Override
    public Optional<House> findActiveHouse(Long houseId) {
        QHouse qHouse = new QHouse("house");

        Optional<House> house = Optional.ofNullable(from(qHouse)
                .where(qHouse.id.eq(houseId).and(qHouse.status.eq(true)))
                .fetchOne());

        return house;
    }


    @Override
    public Optional<House> findActiveHouseForDelete(Long userId, Long houseId) {
        QHouse qHouse = new QHouse("house");
        QUser qUser = new QUser("user");

        Optional<House> house = Optional.ofNullable(from(qHouse)
                .where(qHouse.id.eq(houseId).and(qUser.id.eq(userId)).and(qHouse.status.eq(true)))
                .fetchOne());

        return house;
    }

    @Override
    public Page<House> findByPriceDesc(Pageable pageable){
        QHouse qHouse = new QHouse("house");

        List<House> houses = from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .orderBy(qHouse.price.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<House> findByPriceAsc(Pageable pageable){
        QHouse qHouse = new QHouse("house");

        List<House> houses =from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .orderBy(qHouse.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<House> findByLikeCntDesc(Pageable pageable){
        QHouse qHouse = new QHouse("house");

        List<House> houses =from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .orderBy(qHouse.likeCnt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

//    SELECT *
//    FROM subway
//    ORDER BY (6371
//                      *ACOS(COS(RADIANS('입력받은 latitude'))
//            *COS(RADIANS(subway.latitude))
//            *COS(radians(subway.longitude)-RADIANS('입력받은 longitude'))
//            +SIN(RADIANS('입력받은 latitude'))*SIN(RADIANS(latitude))))
//    LIMIT 3

    @Override
    public Page<House> getNearestHouseList(Pageable pageable, Double latitude, Double longitude) {
        QHouse qHouse = new QHouse("house");

        // latitude 를 radians 로 계산
        NumberExpression<Double> radiansLatitude =
                Expressions.numberTemplate(Double.class, "radians({0})", latitude);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
        NumberExpression<Double> cosSubwayLatitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", qHouse.latitude);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
        NumberExpression<Double> sinSubWayLatitude =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))", qHouse.latitude);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", qHouse.longitude, longitude);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));

        // 최종 계산
        NumberExpression<Double> distanceExpression =
                Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        List<House> findByNearestHouseList = from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .orderBy(distanceExpression.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());


        return new PageImpl<>(findByNearestHouseList.stream()
                .distinct()
                .collect(Collectors.toList()), pageable, pageable.getPageSize());
    }
    @Override
    public Page<House> findByName(Pageable pageable, String name){
        QHouse qHouse = new QHouse("house");

        List<House> houses = from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .where(qHouse.name.like("%"+name+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<House> findByAddress(Pageable pageable, String address) {
        QHouse qHouse = new QHouse("house");

        List<House> houses = from(qHouse)
                .leftJoin(qHouse.houseImageList).fetchJoin()
                .leftJoin(qHouse.user).fetchJoin()
                .where(qHouse.address.like("%"+address+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    /*
    @Override
    public List<House> findHousesWithinDistance(Double baseLat, Double baseLon) {
        QHouse house = QHouse.house;

        NumberTemplate<Double> distanceExpression = DistanceUtils.calculateDistance(
                house.latitude,
                house.longitude,
                Expressions.constant(baseLat),
                Expressions.constant(baseLon)
        );

        List<House> houses = from(house)
                .where(house.status.eq(true).and(distanceExpression.loe(1.0))) // 고정된 반경 1km
                .select(house)
                .fetch();

        houses.sort(Comparator.comparingDouble(h ->
                DistanceUtils.calculateDistance(baseLat, baseLon, h.getLatitude(), h.getLongitude())));

        return houses;
    }


    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return DistanceUtils.EARTH_RADIUS * c;
    }
    */
}
