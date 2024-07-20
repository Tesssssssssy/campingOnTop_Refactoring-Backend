package com.example.campingontop.orders.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateOrdersDtoReq {
    private List<Long> cartIdxList = new ArrayList<>();
}
