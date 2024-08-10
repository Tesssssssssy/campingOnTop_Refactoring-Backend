package com.example.campingontop.domain.mysql.likes.repository;

import com.example.campingontop.domain.mysql.likes.model.Likes;
import com.example.campingontop.domain.mysql.likes.repository.queryDsl.LikesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
    List<Likes> findByUserId(Long userId);

    Likes findByUserIdAndHouseId(Long userId, Long houseId);
}
