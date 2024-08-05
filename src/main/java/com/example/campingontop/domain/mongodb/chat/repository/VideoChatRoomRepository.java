package com.example.campingontop.domain.mongodb.chat.repository;

import com.example.campingontop.domain.mongodb.chat.model.VideoChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoChatRoomRepository extends MongoRepository<VideoChatRoom, String> {
    @Query("{'$or': [{'buyerId': ?0, 'sellerId': ?1, 'houseId': ?2}, {'buyerId': ?1, 'sellerId': ?0, 'houseId': ?2}]}")
    Optional<VideoChatRoom> findByUserIdsAndHouseId(Long userId1, Long userId2, Long houseId);
}
