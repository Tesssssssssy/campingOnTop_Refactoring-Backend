package com.example.campingontop.user.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSetUserImgDtoReq {
    @NotNull
    private MultipartFile img;
}