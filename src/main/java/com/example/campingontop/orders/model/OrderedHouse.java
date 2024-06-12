package com.example.campingontop.orders.model;

import com.example.campingontop.cart.model.Cart;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_id")
    private Cart cart;

    public static OrderedHouse toEntity(Orders orders, Cart cart) {
        return OrderedHouse.builder()
                .orders(orders)
                .cart(cart)
                .build();
    }
}
