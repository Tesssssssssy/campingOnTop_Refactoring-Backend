package com.example.campingontop.orders.repository;

import com.example.campingontop.orders.model.OrderedHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedHouseRespository extends JpaRepository<OrderedHouse, Integer> {
}
