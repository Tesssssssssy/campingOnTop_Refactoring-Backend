package com.example.campingontop.domain.mongodb.chat.repository;

import com.example.campingontop.domain.mongodb.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByRoomId(String roomId);
}