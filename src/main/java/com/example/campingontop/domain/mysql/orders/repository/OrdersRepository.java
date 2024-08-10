package com.example.campingontop.domain.mysql.orders.repository;

import com.example.campingontop.domain.mysql.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByConsumerEmailOrderByIdDesc(String email);
}
