package com.example.campingontop.domain.mongodb.chat.controller;

import com.example.campingontop.domain.mongodb.chat.model.Chat;
import com.example.campingontop.domain.mongodb.chat.model.ChatDto;
import com.example.campingontop.domain.mongodb.chat.model.ChatRoom;
import com.example.campingontop.domain.mongodb.chat.model.ChatRoomDto;
import com.example.campingontop.domain.mongodb.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = chatService.ensureChatRoom(chatRoomDto.getBuyerId(), chatRoomDto.getBuyerNickname(),
                chatRoomDto.getSellerId(), chatRoomDto.getSellerNickname(), chatRoomDto.getHouseId());
        return ResponseEntity.ok(chatRoom);
    }

    @MessageMapping("/send/{chatRoomId}")
    @SendTo("/topic/chat/{chatRoomId}")
    public ChatDto sendMessage(@Payload ChatDto chatDto, @DestinationVariable String chatRoomId) {
        chatDto.setChatRoomId(chatRoomId);
        ChatDto savedChat = chatService.saveChat(chatDto);
        log.debug("Message sent to room {}: {}", chatRoomId, savedChat);
        return savedChat;
    }

    @GetMapping("/room/{chatRoomId}")
    public ResponseEntity<List<ChatDto>> getChatsByRoomId(@PathVariable String chatRoomId) {
        List<ChatDto> chats = chatService.getChatsByRoomId(chatRoomId);
        return ResponseEntity.ok(chats);
    }
}
