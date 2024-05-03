package com.example.campingontop.orderedHouse.repository;

import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.orderedHouse.repository.queryDsl.OrderedHouseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedHouseRepository extends JpaRepository<OrderedHouse, Long>, OrderedHouseRepositoryCustom {
}
