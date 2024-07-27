package com.example.campingontop.domain.mongodb.chat.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Document(collection = "chats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    private String id;
    private String chatRoomId;
    private Long senderId;
    private String senderNickname;
    private String message;

    @CreatedDate
    private LocalDateTime date;

    public static ChatDto convertToDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .chatRoomId(chat.getChatRoomId())
                .senderId(chat.getSenderId())
                .senderNickname(chat.getSenderNickname()) // Ensure senderNickname is included
                .message(chat.getMessage())
                .date(chat.getDate().atZone(ZoneId.of("Asia/Seoul")).toOffsetDateTime()) // Convert LocalDateTime to OffsetDateTime
                .build();
    }
}
