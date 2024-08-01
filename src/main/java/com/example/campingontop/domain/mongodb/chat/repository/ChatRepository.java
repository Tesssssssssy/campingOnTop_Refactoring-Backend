package com.example.campingontop.domain.mongodb.chat.repository;

import com.example.campingontop.domain.mongodb.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByChatRoomId(String chatRoomId);
}