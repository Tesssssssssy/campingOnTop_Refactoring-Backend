package com.example.campingontop.cart.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateCartReq {
    private Long houseId;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkIn;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut;
}
