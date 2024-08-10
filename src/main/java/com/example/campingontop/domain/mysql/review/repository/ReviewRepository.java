package com.example.campingontop.domain.mysql.review.repository;

import com.example.campingontop.domain.mysql.review.model.Review;
import com.example.campingontop.domain.mysql.review.repository.queryDsl.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Optional<Review> findByIdAndUserId(Long reviewId, Long userId);

    // 결제 내역 ID로 유효한 리뷰 조회
    Optional<Review> findByOrderedHouseIdAndStatus(Long orderedHouseId, boolean status);
}
