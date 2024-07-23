package com.example.campingontop.coupon.model;

import com.example.campingontop.coupon.constant.Event;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Event event;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date expiryTime;

    @Comment("0: 비활성화 | 1: 활성화")
    private Boolean status;

    @PrePersist
    void onPrePersist() {
        this.createdAt = new Date();
        this.expiryTime = Timestamp.from(Instant.now().plus(2, ChronoUnit.WEEKS));
    }
}
