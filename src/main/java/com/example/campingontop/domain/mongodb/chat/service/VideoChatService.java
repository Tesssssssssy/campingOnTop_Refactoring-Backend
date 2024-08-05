package com.example.campingontop.domain.mongodb.chat.service;

import com.example.campingontop.domain.mongodb.chat.model.SignalMessage;
import com.example.campingontop.domain.mongodb.chat.model.VideoChat;
import com.example.campingontop.domain.mongodb.chat.model.VideoChatDto;
import com.example.campingontop.domain.mongodb.chat.model.VideoChatRoom;
import com.example.campingontop.domain.mongodb.chat.repository.VideoChatRepository;
import com.example.campingontop.domain.mongodb.chat.repository.VideoChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final VideoChatRoomRepository videoChatRoomRepository;
    private final VideoChatRepository videoChatRepository;

    @Transactional
    public VideoChatRoom ensureVideoChatRoom(Long buyerId, String buyerNickname, Long sellerId, String sellerNickname, Long houseId) {
        VideoChatRoom existingRoom = findExistingVideoChatRoom(buyerId, sellerId, houseId);
        if (existingRoom != null) {
            return existingRoom;
        }

        VideoChatRoom newRoom = VideoChatRoom.builder()
                .id(UUID.randomUUID().toString())
                .buyerId(buyerId)
                .buyerNickname(buyerNickname)
                .sellerId(sellerId)
                .sellerNickname(sellerNickname)
                .houseId(houseId)
                .build();
        return videoChatRoomRepository.save(newRoom);
    }

    @Transactional
    public VideoChatRoom findExistingVideoChatRoom(Long user1Id, Long user2Id, Long houseId) {
        return videoChatRoomRepository.findByUserIdsAndHouseId(user1Id, user2Id, houseId)
                .orElse(null);
    }

    public List<VideoChatDto> getVideoChatsByRoomId(String videoChatRoomId) {
        List<VideoChat> videoChats = videoChatRepository.findByVideoChatRoomId(videoChatRoomId);
        return videoChats.stream().map(VideoChat::convertToDto).collect(Collectors.toList());
    }

    public VideoChatRoom getVideoChatRoomById(String videoChatRoomId) {
        return videoChatRoomRepository.findById(videoChatRoomId)
                .orElseThrow(() -> new RuntimeException("VideoChatRoom not found"));
    }

    public void sendMessageToRoom(String videoChatRoomId, SignalMessage signalMessage) {
        messagingTemplate.convertAndSend("/topic/video-chat/" + videoChatRoomId, signalMessage);
    }
}