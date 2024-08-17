package com.example.campingontop.domain.mysql.cart.model.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateCartReq {
    private Long houseId;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkIn;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut;
}
