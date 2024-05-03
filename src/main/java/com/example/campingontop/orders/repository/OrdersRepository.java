package com.example.campingontop.orders.repository;

import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByCart_User(User user);
}
