package com.example.campingontop.domain.mysql.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyEmailCert {
    private Long id;
    private String email;
    private String uuid;
}

