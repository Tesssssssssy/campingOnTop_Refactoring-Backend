package com.example.campingontop.domain.mongodb.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "videochatrooms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoChatRoom {
    @Id
    private String id;
    private Long buyerId;
    private String buyerNickname;
    private Long sellerId;
    private String sellerNickname;
    private Long houseId;
}
