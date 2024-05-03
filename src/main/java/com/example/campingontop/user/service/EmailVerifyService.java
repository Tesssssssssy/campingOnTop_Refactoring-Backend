package com.example.campingontop.user.service;

import com.example.campingontop.user.model.EmailVerify;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.model.request.PostEmailConfirmDtoReq;
import com.example.campingontop.user.model.request.VerifyEmailCert;
import com.example.campingontop.user.repository.EmailVerifyRepository;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;
    private final OpenFeignVerifyEmailCertClient openFeignVerifyEmailCertClient;
    private final UserRepository userRepository;

    public void create(String email, String token) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .token(token)
                .build();
        emailVerifyRepository.save(emailVerify);
    }

    public Boolean verify(PostEmailConfirmDtoReq req) {
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(req.getEmail());
        if(result.isPresent()){
            EmailVerify emailVerify = result.get();
            if(emailVerify.getToken().equals(req.getToken())) {
                return true;
            }
        }
        return false;
    }

    public VerifyEmailCert verifyMsa(String email, String uuid) {
        VerifyEmailCert verifyEmailCert = VerifyEmailCert.builder()
                .uuid(uuid)
                .email(email)
                .build();

        VerifyEmailCert response = openFeignVerifyEmailCertClient.call(verifyEmailCert.getEmail(), verifyEmailCert.getUuid());

        if (response != null) {
            Optional<User> result = userRepository.findByEmail(email);
            if (result.isPresent()) {
                User user = result.get();
                user.setStatus(true);
                userRepository.save(user);
            }
        }

        return response;
    }
}
