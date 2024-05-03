package com.example.campingontop.review.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSetReviewImgDtoReq {
    @NotNull
    private MultipartFile[] img;
}
