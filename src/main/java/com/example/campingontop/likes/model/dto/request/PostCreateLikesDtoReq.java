package com.example.campingontop.likes.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateLikesDtoReq {
    private Long userId;
    private Long houseId;
}
