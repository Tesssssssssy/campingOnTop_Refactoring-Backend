package com.example.campingontop.review.repository;
import com.example.campingontop.house.model.House;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.repository.queryDsl.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    Review findReviewById(Long reviewId);
    Optional<Review> findByContent(String content);
    List<Review> findAllByOrderByCreatedAtDesc(); // createdAt 기준 최신 순 조회
    List<Review> findAllByOrderByRatingDesc(); // 별점 높은 순 조회
    List<Review> findAllByOrderByRatingAsc(); // 별점 낮은 순 조회
    @Query("SELECT AVG(r.rating) FROM Review r")
    Double findAverageRating();
}