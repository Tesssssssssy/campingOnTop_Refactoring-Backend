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
public class GetFindUserDtoRes {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String phoneNum;
    private Gender gender;
    private String birthDay;
    private Date createdAt;
    private Date updatedAt;

    public static GetFindUserDtoRes toDto(User user) {
        return GetFindUserDtoRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .phoneNum(user.getPhoneNum())
                .gender(user.getGender())
                .birthDay(user.getBirthDay())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
