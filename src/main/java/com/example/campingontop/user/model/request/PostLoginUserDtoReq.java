package com.example.campingontop.user.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLoginUserDtoReq {
    private String username;
    private String password;
}
