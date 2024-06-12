package com.example.campingontop.user.model.response;

import com.example.campingontop.enums.Gender;
import com.example.campingontop.user.model.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSignUpUserDtoRes {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String phoneNum;
    private Gender gender;
    private String birthDay;
    private Boolean status;
    private String authority;
    private Date createdAt;

    public static PostSignUpUserDtoRes toDto(User user) {
        return PostSignUpUserDtoRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .phoneNum(user.getPhoneNum())
                .gender(user.getGender())
                .birthDay(user.getBirthDay())
                .status(user.getStatus())
                .authority(user.getAuthority())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
