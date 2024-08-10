package com.example.campingontop.domain.mysql.houseImage.model;

import com.example.campingontop.domain.mysql.house.model.House;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "House_id")
    private House house;
}
