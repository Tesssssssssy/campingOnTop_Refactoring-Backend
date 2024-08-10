package com.example.campingontop.domain.mysql.coupon.model;

import com.example.campingontop.domain.mysql.coupon.constant.Event;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Calendar;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_YEAR, 2); // 현재 시간에 2주 추가
        this.expiryTime = calendar.getTime();
    }
}
