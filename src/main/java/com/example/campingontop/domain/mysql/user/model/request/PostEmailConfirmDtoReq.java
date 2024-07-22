package com.example.campingontop.domain.mysql.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEmailConfirmDtoReq {
    private String email;
    private String token;
    private String jwt;
}
