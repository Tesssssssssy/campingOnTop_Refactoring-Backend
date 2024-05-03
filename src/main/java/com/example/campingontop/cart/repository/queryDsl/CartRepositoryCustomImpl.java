package com.example.campingontop.cart.repository.queryDsl;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.model.QCart;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class CartRepositoryCustomImpl extends QuerydslRepositorySupport implements CartRepositoryCustom {
    public CartRepositoryCustomImpl() {
        super(Cart.class);
    }

    @Override
    public Cart findByUserIdAndCartId(Long userId, Long cartId) {
        QCart cart = new QCart("cart");

        if (userId == null || cartId == null) {
            throw new IllegalArgumentException("UserId and CartId cannot be null");
        }

        System.out.println("userId: " + userId + ", cartId: " + cartId);

        Cart result = from(cart)
                .where(cart.user.id.eq(userId).and(cart.house.id.eq(cartId)))
                .distinct().fetchOne();
        return result;
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        QCart cart = new QCart("cart");

        if (userId == null) {
            throw new IllegalArgumentException("User ID는 필수 입력 필드입니다.");
        }

        List<Cart> result = from(cart)
                .leftJoin(cart.user).fetchJoin()
                .leftJoin(cart.house).fetchJoin()
                .where(cart.user.id.eq(userId).and(cart.status.eq(true)))
                .distinct()
                .fetch();

        return result;
    }


}
