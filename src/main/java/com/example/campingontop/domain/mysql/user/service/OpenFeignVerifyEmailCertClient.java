package com.example.campingontop.domain.mysql.user.service;

import com.example.campingontop.domain.mysql.user.model.request.VerifyEmailCert;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EmailCert", url = "http://localhost:8082/emailcert/verify")
public interface OpenFeignVerifyEmailCertClient {
    @GetMapping
    VerifyEmailCert call(@RequestParam("email") String email, @RequestParam("uuid") String uuid);
}
