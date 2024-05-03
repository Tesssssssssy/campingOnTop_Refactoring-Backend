package com.example.campingontop.cart.model;

import com.example.campingontop.house.model.House;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.user.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "House_id")
    private House house;

    @OneToOne(mappedBy = "cart")
    private Orders orders;


    private LocalDate checkIn;
    private LocalDate checkOut;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;

    private Integer price;

    private Boolean status;


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
