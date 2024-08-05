package com.example.campingontop.domain.mongodb.chat.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoChatDto {
    private String id;
    private String videoChatRoomId;
    private Long senderId;
    private String senderNickname;
    private String type; // 'video-offer', 'video-answer', 'new-ice-candidate'
    private Object payload;
}
