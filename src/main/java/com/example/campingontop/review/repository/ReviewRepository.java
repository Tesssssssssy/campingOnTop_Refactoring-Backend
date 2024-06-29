package com.example.campingontop.review.repository;

import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.repository.queryDsl.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Optional<Review> findByIdxAndUserIdx(Long reviewId, Long userId);
}
