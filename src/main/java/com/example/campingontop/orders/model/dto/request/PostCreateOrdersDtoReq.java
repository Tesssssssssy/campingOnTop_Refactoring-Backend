package com.example.campingontop.orders.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostCreateOrdersDtoReq {
    private List<Long> cartIdxList = new ArrayList<>();
}
