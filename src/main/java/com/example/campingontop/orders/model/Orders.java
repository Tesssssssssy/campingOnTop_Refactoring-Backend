package com.example.campingontop.orders.model;

import com.example.campingontop.cart.model.Cart;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(nullable=false)
    private String impUid;

    @Column(nullable=false)
    private LocalDate orderDate;

    private Integer price;

    private Long consumerId;
    private String consumerEmail;
    private String consumerPassword;
    private String consumerAddress;
    private String consumerName;
    private String consumerPhoneNum;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<OrderedHouse> orderedHouseList = new ArrayList<>();

    public static Orders toEntity(String impUid, String email, Integer price) {
        return Orders.builder()
                .impUid(impUid)
                .consumerEmail(email)
                .price(price)
                .orderDate(LocalDate.now())
                .build();
    }
}
