package com.example.campingontop.domain.mysql.orders.repository;

import com.example.campingontop.domain.mysql.orders.model.OrderedHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedHouseRespository extends JpaRepository<OrderedHouse, Long> {
}
