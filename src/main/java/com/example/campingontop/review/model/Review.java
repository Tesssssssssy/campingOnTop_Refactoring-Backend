package com.example.campingontop.review.model;

import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.reviewImage.model.ReviewImage;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int rating;

    @Comment("0: 비활성화 | 1: 활성화")
    private Boolean status;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImageList = new ArrayList<>(); // 'houseImageList' 이름 변경 및 'mappedBy' 속성 수정

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Orders_id")
    private Orders orders;


    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}