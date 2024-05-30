package com.example.campingontop.coupon.model;

import com.example.campingontop.user.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_idx")
    private User user;

    @Column(nullable = false, length = 45)
    private String couponName;

    @Column(nullable = false)
    private Integer couponDiscountPrice;

    @Column(nullable = false)
    private String receivedDate;

    @Column(nullable = false)
    private String couponExpirationDate;

    @Column(nullable = false)
    private Boolean status;
}
