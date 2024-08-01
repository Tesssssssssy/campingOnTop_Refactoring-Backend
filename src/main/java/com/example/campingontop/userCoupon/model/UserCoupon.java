package com.example.campingontop.userCoupon.model;

import com.example.campingontop.coupon.model.Coupon;
import com.example.campingontop.user.model.User;
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

    @Column(nullable = false)
    private Date expiryTime;

    @Column(nullable = false)
    private boolean isUsed;

    @PrePersist
    void onPrePersist() {
        this.createdAt = new Date();
        if (this.expiryTime == null) {
            this.expiryTime = coupon.getExpiryTime();
        }
    }

}
