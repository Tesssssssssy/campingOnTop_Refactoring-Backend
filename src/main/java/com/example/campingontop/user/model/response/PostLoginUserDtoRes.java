package com.example.campingontop.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLoginUserDtoRes {
    private String token;
}
