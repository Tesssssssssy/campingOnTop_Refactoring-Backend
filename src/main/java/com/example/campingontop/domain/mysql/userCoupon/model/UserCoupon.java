package com.example.campingontop.domain.mysql.userCoupon.model;

import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import com.example.campingontop.domain.mysql.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    private boolean isUsed;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
