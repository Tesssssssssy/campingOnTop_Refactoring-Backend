package com.example.campingontop.domain.mysql.cart.repository.queryDsl;

import com.example.campingontop.domain.mysql.cart.model.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    Cart findByUserIdAndCartId(Long userId, Long cartId);

    List<Cart> findByUserId(Long userId);
}
