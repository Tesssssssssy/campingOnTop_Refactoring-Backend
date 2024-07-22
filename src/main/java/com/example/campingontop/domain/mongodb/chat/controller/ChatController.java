package com.example.campingontop.domain.mongodb.chat.controller;


import com.example.campingontop.domain.mongodb.chat.model.ChatDto;
import com.example.campingontop.domain.mongodb.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public ChatDto sendChat(@RequestBody ChatDto chatDto) {
        return chatService.saveChat(chatDto);
    }

    @GetMapping("/room/{roomId}")
    public List<ChatDto> getChatsByRoomId(@PathVariable String roomId) {
        return chatService.getChatsByRoomId(roomId);
    }
}