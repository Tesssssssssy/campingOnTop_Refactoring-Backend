package com.example.campingontop.cart.repository.queryDsl;

import com.example.campingontop.cart.model.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    Cart findByUserIdAndCartId(Long userId, Long cartId);

    List<Cart> findByUserId(Long userId);
}
