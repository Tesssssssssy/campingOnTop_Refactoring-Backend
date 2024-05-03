package com.example.campingontop.user.model;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.enums.Gender;
import com.example.campingontop.house.model.House;
import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.user.model.request.PostCreateUserDtoReq;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String authority;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String nickName;

    @Column(length = 50, nullable = false, unique = true)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Gender gender;

    @Column(length = 30)
    private String birthDay;

    @ColumnDefault("1")
    @Comment("0: 비활성화 | 1: 활성화")
    private Boolean status;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;


    @OneToMany(mappedBy = "user")
    private List<House> houseList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Cart> cartList = new ArrayList<>();



    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User toEntity(PostCreateUserDtoReq req) {
        return User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .nickName(req.getNickName())
                .phoneNum(req.getPhoneNum())
                .gender(Gender.fromValue(req.getGender()))
                .birthDay(req.getBirthDay())
                .status(false)
                .build();
    }
}
