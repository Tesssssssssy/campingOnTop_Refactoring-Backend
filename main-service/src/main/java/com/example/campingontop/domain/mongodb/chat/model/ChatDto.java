package com.example.campingontop.domain.mongodb.chat.model;

import lombok.*;

import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private String id;
    private String chatRoomId;
    private Long senderId;
    private String senderNickname;
    private String message;
    private OffsetDateTime date;

    public static Chat convertToEntity(ChatDto chatDto) {
        return Chat.builder()
                .id(chatDto.getId())
                .chatRoomId(chatDto.getChatRoomId())
                .senderId(chatDto.getSenderId())
                .senderNickname(chatDto.getSenderNickname())
                .message(chatDto.getMessage())
                .date(chatDto.getDate().toLocalDateTime())
                .build();
    }
}
