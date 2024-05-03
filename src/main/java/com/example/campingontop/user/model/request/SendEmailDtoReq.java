package com.example.campingontop.user.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailDtoReq {
    private Long id;
    private String email;
    private String nickName;

    public static SendEmailDtoReq toDto(Long id, String email, String nickName) {
        return SendEmailDtoReq.builder()
                .id(id)
                .email(email)
                .nickName(nickName)
                .build();
    }
}
