package com.example.campingontop.domain.mysql.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLoginUserDtoRes {
    private String token;
    private String refreshToken;
}
