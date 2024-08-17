package com.example.campingontop.domain.mysql.cart.repository;

import com.example.campingontop.domain.mysql.cart.model.Cart;
import com.example.campingontop.domain.mysql.cart.repository.queryDsl.CartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
    Optional<Cart> findByUser_IdAndHouse_IdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Long userId, Long houseId, LocalDate checkOut, LocalDate checkIn);

}
