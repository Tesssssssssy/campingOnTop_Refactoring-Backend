package com.example.campingontop.domain.mongodb.chat.service;

import com.example.campingontop.domain.mongodb.chat.model.ChatRoom;
import com.example.campingontop.domain.mongodb.chat.model.ChatRoomDto;
import com.example.campingontop.domain.mongodb.chat.repository.ChatRoomRepository;
import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final HouseRepository houseRepository;

    public List<ChatRoomDto> getChatRoomsByUserId(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerIdOrSellerId(userId, userId);
        List<ChatRoomDto> chatRoomDtos = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            chatRoomDtos.add(convertToDto(chatRoom));
        }
        return chatRoomDtos;
    }

    public ChatRoomDto getChatRoomById(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        return convertToDto(chatRoom);
    }

    private ChatRoomDto convertToDto(ChatRoom chatRoom) {
        String houseName = houseRepository.findById(chatRoom.getHouseId())
                .map(House::getName)
                .orElse("No House Found");

        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .buyerId(chatRoom.getBuyerId())
                .buyerNickname(chatRoom.getBuyerNickname())
                .sellerId(chatRoom.getSellerId())
                .sellerNickname(chatRoom.getSellerNickname())
                .houseName(houseName)
                .houseId(chatRoom.getHouseId())
                .build();
    }
}