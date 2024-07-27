package com.example.campingontop.domain.mongodb.chat.service;

import com.example.campingontop.domain.mongodb.chat.model.Chat;
import com.example.campingontop.domain.mongodb.chat.model.ChatDto;
import com.example.campingontop.domain.mongodb.chat.model.ChatRoom;
import com.example.campingontop.domain.mongodb.chat.model.ChatRoomDto;
import com.example.campingontop.domain.mongodb.chat.repository.ChatRepository;
import com.example.campingontop.domain.mongodb.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public ChatRoom ensureChatRoom(Long buyerId, String buyerNickname, Long sellerId, String sellerNickname, Long houseId) {
        return chatRoomRepository.findByBuyerIdAndSellerId(buyerId, sellerId)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .id(UUID.randomUUID().toString())
                            .buyerId(buyerId)
                            .buyerNickname(buyerNickname)
                            .sellerId(sellerId)
                            .sellerNickname(sellerNickname)
                            .houseId(houseId)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }

    public List<ChatDto> getChatsByRoomId(String chatRoomId) {
        List<Chat> chats = chatRepository.findByChatRoomId(chatRoomId);
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat : chats) {
            chatDtos.add(Chat.convertToDto(chat));
        }
        return chatDtos;
    }

    public ChatDto saveChat(ChatDto chatDto) {
        Chat chat = ChatDto.convertToEntity(chatDto);
        chat = chatRepository.save(chat);
        return Chat.convertToDto(chat);
    }
}