package com.example.campingontop.user.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostEmailConfirmDtoReq {
    private String email;
    private String token;
    private String jwt;
}
