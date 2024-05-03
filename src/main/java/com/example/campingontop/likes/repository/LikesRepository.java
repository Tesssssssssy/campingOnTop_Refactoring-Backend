package com.example.campingontop.likes.repository;

import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.likes.repository.queryDsl.LikesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
    List<Likes> findByUserId(Long userId);

    boolean existsByUserIdAndHouseId(Long userId, Long HouseId);
}
