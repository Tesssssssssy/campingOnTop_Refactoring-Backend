package com.example.campingontop.domain.mongodb.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private String senderId;
    private String senderNickname;
    private String recipientId;
    private String recipientNickname;
    private String message;
    private String date;
}
