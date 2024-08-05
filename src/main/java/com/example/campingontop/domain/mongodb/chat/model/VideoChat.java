package com.example.campingontop.domain.mongodb.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "videochats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoChat {
    @Id
    private String id;
    private String videoChatRoomId;
    private Long senderId;
    private String senderNickname;
    private String type; // 'video-offer', 'video-answer', 'new-ice-candidate'
    private Object payload;

    public static VideoChatDto convertToDto(VideoChat videoChat) {
        return VideoChatDto.builder()
                .id(videoChat.getId())
                .videoChatRoomId(videoChat.getVideoChatRoomId())
                .senderId(videoChat.getSenderId())
                .senderNickname(videoChat.getSenderNickname())
                .type(videoChat.getType())
                .payload(videoChat.getPayload())
                .build();
    }
}
