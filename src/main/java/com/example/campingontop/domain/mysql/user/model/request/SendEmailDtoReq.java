package com.example.campingontop.domain.mysql.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
