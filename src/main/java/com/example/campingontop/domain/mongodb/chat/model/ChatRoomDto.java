package com.example.campingontop.domain.mongodb.chat.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private String id;
    private Long buyerId;
    private String buyerNickname;
    private Long sellerId;
    private String sellerNickname;
    private String houseName;
    private Long houseId;

    public String getChatPartnerNickname(Long userId) {
        return userId.equals(buyerId) ? sellerNickname : buyerNickname;
    }
}
