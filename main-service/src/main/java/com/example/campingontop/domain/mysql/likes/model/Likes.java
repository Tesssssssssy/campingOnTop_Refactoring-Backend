package com.example.campingontop.domain.mysql.likes.model;

import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.user.model.User;
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
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "House_id")
    private House house;

    private Boolean status;
}
