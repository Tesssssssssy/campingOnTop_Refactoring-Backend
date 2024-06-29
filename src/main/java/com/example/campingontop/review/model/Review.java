package com.example.campingontop.review.model;

import com.example.campingontop.house.model.House;
import com.example.campingontop.orders.model.OrderedHouse;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.user.model.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer stars;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;

    @Comment("0: 비활성화 | 1: 활성화")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY)
    private OrderedHouse orderedHouse;
}
