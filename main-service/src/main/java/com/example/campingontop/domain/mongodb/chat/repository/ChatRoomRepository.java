package com.example.campingontop.domain.mongodb.chat.repository;

import com.example.campingontop.domain.mongodb.chat.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByBuyerIdAndSellerId(Long buyerId, Long sellerId);
    List<ChatRoom> findByBuyerIdOrSellerId(Long buyerId, Long sellerId);
}