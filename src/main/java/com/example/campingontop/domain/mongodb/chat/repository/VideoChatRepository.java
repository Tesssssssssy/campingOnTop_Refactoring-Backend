package com.example.campingontop.domain.mongodb.chat.repository;

import com.example.campingontop.domain.mongodb.chat.model.VideoChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoChatRepository extends MongoRepository<VideoChat, String> {
    List<VideoChat> findByVideoChatRoomId(String videoChatRoomId);
}
