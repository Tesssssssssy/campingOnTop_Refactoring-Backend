package com.example.campingontop.house.model;

import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @Min(0)
    private Integer price;

    private String address;

    private Double latitude;

    private Double longitude;

    @Min(1)
    private Integer maxUser;

    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasWashingMachine;

    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasAirConditioner;

    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasBed;

    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasHeater;

    @Comment("0: 비활성화 | 1: 활성화")
    private Boolean status;

    private Integer likeCnt;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HouseImage> houseImageList = new ArrayList<>();

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<OrderedHouse> orderedHouseList = new ArrayList<>();

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<Likes> likesList = new ArrayList<>();


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    public void increaseLikeCount() {
        this.likeCnt = this.likeCnt + 1;
    }
}
