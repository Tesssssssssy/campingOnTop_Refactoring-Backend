package com.example.campingontop.domain.mongodb.chat.controller;

import com.example.campingontop.domain.mongodb.chat.model.ChatRoomDto;
import com.example.campingontop.domain.mongodb.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomsByUserId(@PathVariable Long userId) {
        List<ChatRoomDto> chatRooms = chatRoomService.getChatRoomsByUserId(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDto> getChatRoomById(@PathVariable String chatRoomId) {
        ChatRoomDto chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        return ResponseEntity.ok(chatRoom);
    }
}