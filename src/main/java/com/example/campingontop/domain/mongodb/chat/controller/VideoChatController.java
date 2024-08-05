package com.example.campingontop.domain.mongodb.chat.controller;

import com.example.campingontop.domain.mongodb.chat.model.*;
import com.example.campingontop.domain.mongodb.chat.service.VideoChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/video-chat")
public class VideoChatController {
    private final VideoChatService videoChatService;

    @PostMapping("/room")
    public ResponseEntity<VideoChatRoom> createOrJoinVideoChatRoom(@RequestBody VideoChatRoomDto videoChatRoomDto) {
        VideoChatRoom videoChatRoom = videoChatService.ensureVideoChatRoom(videoChatRoomDto.getBuyerId(), videoChatRoomDto.getBuyerNickname(),
                videoChatRoomDto.getSellerId(), videoChatRoomDto.getSellerNickname(), videoChatRoomDto.getHouseId());
        return ResponseEntity.ok(videoChatRoom);
    }

    @GetMapping("/room/{user1Id}/{user2Id}/{houseId}")
    public ResponseEntity<VideoChatRoom> findExistingVideoChatRoom(@PathVariable Long user1Id, @PathVariable Long user2Id, @PathVariable Long houseId) {
        VideoChatRoom videoChatRoom = videoChatService.findExistingVideoChatRoom(user1Id, user2Id, houseId);
        return ResponseEntity.ok(videoChatRoom);
    }

    @MessageMapping("/video-chat-room/send/{videoChatRoomId}")
    public void sendVideoMessage(@Payload SignalMessage signalMessage, @DestinationVariable String videoChatRoomId) {
        log.debug("Video message sent to room {}: {}", videoChatRoomId, signalMessage);
        videoChatService.sendMessageToRoom(videoChatRoomId, signalMessage);
    }

    @GetMapping("/room/{videoChatRoomId}")
    public ResponseEntity<VideoChatRoom> getVideoChatRoomById(@PathVariable String videoChatRoomId) {
        VideoChatRoom videoChatRoom = videoChatService.getVideoChatRoomById(videoChatRoomId);
        return ResponseEntity.ok(videoChatRoom);
    }
}