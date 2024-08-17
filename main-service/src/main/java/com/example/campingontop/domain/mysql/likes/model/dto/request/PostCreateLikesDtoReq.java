package com.example.campingontop.domain.mysql.likes.model.dto.request;

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
