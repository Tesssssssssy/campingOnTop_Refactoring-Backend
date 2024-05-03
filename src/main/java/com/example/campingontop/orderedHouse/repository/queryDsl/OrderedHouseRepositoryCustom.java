package com.example.campingontop.orderedHouse.repository.queryDsl;

import com.example.campingontop.orderedHouse.model.OrderedHouse;

import java.util.List;

public interface OrderedHouseRepositoryCustom {
    List<OrderedHouse> findOrderedListByUserId(Long userId);

}
