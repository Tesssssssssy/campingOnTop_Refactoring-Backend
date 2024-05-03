package com.example.campingontop.orders.model;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.review.model.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUid;

    private Integer price;
    private String merchantUid;
    private String paymentStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<OrderedHouse> orderedHouseList = new ArrayList<>();

    @OneToOne(mappedBy = "orders", fetch = FetchType.LAZY)
    private Review review;

    @PrePersist
    public void prePersist() {
        if (this.orderedHouseList == null) {
            this.orderedHouseList = new ArrayList<>();
        }
    }
}
