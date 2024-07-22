package com.example.campingontop.domain.mysql.house.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSetHouseImgDtoReq {
    @NotNull
    private MultipartFile[] img;
}
