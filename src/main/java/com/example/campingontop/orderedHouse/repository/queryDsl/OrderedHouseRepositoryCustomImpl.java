package com.example.campingontop.orderedHouse.repository.queryDsl;

import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.orderedHouse.model.QOrderedHouse;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class OrderedHouseRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderedHouseRepositoryCustom{
    public OrderedHouseRepositoryCustomImpl() {
        super(OrderedHouse.class);
    }


    @Override
    public List<OrderedHouse> findOrderedListByUserId(Long userId) {
        QOrderedHouse orderedHouse = new QOrderedHouse("orderedHouse");

        List<OrderedHouse> result = from(orderedHouse)
                .leftJoin(orderedHouse.house).fetchJoin()
                .leftJoin(orderedHouse.orders).fetchJoin()
                .where(orderedHouse.orders.cart.user.id.eq(userId))
                .distinct()
                .fetch().stream().collect(Collectors.toList());

        return result;
    }

}
