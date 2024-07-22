package com.example.campingontop.domain.mysql.review.service;

import com.example.campingontop.domain.mysql.review.model.request.PatchUpdateReviewDtoReq;
import com.example.campingontop.domain.mysql.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.domain.mysql.review.model.response.*;
import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrderedHouseException;
import com.example.campingontop.exception.entityException.ReviewException;
import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.house.repository.HouseRepository;
import com.example.campingontop.domain.mysql.orders.model.OrderedHouse;
import com.example.campingontop.domain.mysql.orders.repository.OrderedHouseRespository;
import com.example.campingontop.domain.mysql.review.model.Review;
import com.example.campingontop.domain.mysql.review.repository.ReviewRepository;
import com.example.campingontop.domain.mysql.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final OrderedHouseRespository orderedHouseRespository;

    private final HouseRepository houseRepository;

    @Transactional(readOnly = false)
    public PostCreateReviewDtoRes createReview(User user, PostCreateReviewDtoReq postCreateReviewDtoReq) {
        try {
            // 결제 내역 ID로 유효한 리뷰와 삭제된 리뷰 모두 조회
            Optional<Review> existingActiveReview = reviewRepository.findByOrderedHouseIdAndStatus(
                    postCreateReviewDtoReq.getOrderedHouseId(), true);
            Optional<Review> existingDeletedReview = reviewRepository.findByOrderedHouseIdAndStatus(
                    postCreateReviewDtoReq.getOrderedHouseId(), false);

            // 유효한 리뷰가 이미 존재하는 경우 예외 처리
            if (existingActiveReview.isPresent()) {
                throw new ReviewException(ErrorCode.DUPLICATE_REVIEW);
            }

            // 삭제된 리뷰가 이미 존재하는 경우 예외 처리
            if (existingDeletedReview.isPresent()) {
                throw new ReviewException(ErrorCode.REVIEW_ALREADY_DELETED);
            }

            OrderedHouse orderedHouse = orderedHouseRespository.findById(postCreateReviewDtoReq.getOrderedHouseId())
                    .orElseThrow(() -> new OrderedHouseException(ErrorCode.ORDEREDHOUSE_NOT_EXIST));

            Optional<House> searchHouse = houseRepository.findActiveHouse(orderedHouse.getCart().getHouse().getId());
            if (searchHouse.isEmpty()) {
                throw new ReviewException(ErrorCode.HOUSE_NOT_EXIST);
            }

            Review review = Review.builder()
                    .user(user)
                    .content(postCreateReviewDtoReq.getContent())
                    .stars(postCreateReviewDtoReq.getStars())
                    .createdAt(Timestamp.from(Instant.now()))
                    .updatedAt(Timestamp.from(Instant.now()))
                    .status(true)
                    .orderedHouse(orderedHouse)
                    .build();

            review = reviewRepository.save(review);

            PostCreateReviewDtoRes postCreateReviewDtoRes = PostCreateReviewDtoRes.builder()
                    .reviewId(review.getId())
                    .houseName(review.getOrderedHouse().getCart().getHouse().getName())
                    .ordersId(review.getOrderedHouse().getOrders().getId())
                    .content(review.getContent())
                    .stars(review.getStars())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();

            House house = searchHouse.get();
            house.increseReviewCount();
            houseRepository.save(house);

            return postCreateReviewDtoRes;
        } catch (ReviewException e) {
            log.error("ReviewException 발생: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생: {}", e.getMessage(), e);
            throw new ReviewException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(readOnly = true)
    public GetFindReviewByUserIdDtoResResult findReviewByUserId(User user){
        List<Review> list = reviewRepository.findReviewByUserId(user.getId());
        List<GetFindReviewByUserIdDtoRes> getFindReviewByUserIdDtoResList = new ArrayList<>();

        for(Review review : list){
            GetFindReviewByUserIdDtoRes getFindReviewByUserIdDtoRes = GetFindReviewByUserIdDtoRes.builder()
                    .reviewId(review.getId())
                    .houseName(review.getOrderedHouse().getCart().getHouse().getName())
                    .ordersNum(review.getOrderedHouse().getOrders().getId())
                    .reviewContent(review.getContent())
                    .stars(review.getStars())
                    .updatedAt(review.getUpdatedAt())
                    .build();

            getFindReviewByUserIdDtoResList.add(getFindReviewByUserIdDtoRes);
        }

        GetFindReviewByUserIdDtoResResult result = GetFindReviewByUserIdDtoResResult.builder()
                .list(getFindReviewByUserIdDtoResList)
                .build();

        return result;
    }

    @Transactional(readOnly = true)
    public GetFindReviewByHouseIdDtoResResult findReviewByHouseId(Long houseId){
        List<Review> reviewList = reviewRepository.findReviewByHouseId(houseId);
        List<GetFindReviewByHouseIdDtoRes> getFindReviewByHouseIdDtoResList = new ArrayList<>();

        for(Review review : reviewList){
            GetFindReviewByHouseIdDtoRes getFindReviewByHouseIdDtoRes = GetFindReviewByHouseIdDtoRes.builder()
                    .userNickName(review.getUser().getNickName())
                    .reviewContent(review.getContent())
                    .stars(review.getStars())
                    .updatedAt(review.getUpdatedAt())
                    .build();

            getFindReviewByHouseIdDtoResList.add(getFindReviewByHouseIdDtoRes);
        }

        GetFindReviewByHouseIdDtoResResult result = GetFindReviewByHouseIdDtoResResult.builder()
                .list(getFindReviewByHouseIdDtoResList)
                .build();

        return result;
    }

    @Transactional(readOnly = false)
    public PatchUpdateReviewDtoRes updateReview(User user, PatchUpdateReviewDtoReq patchUpdateReviewDtoReq){
        Optional<Review> result = reviewRepository.findByIdAndUserId(patchUpdateReviewDtoReq.getReviewId(), user.getId());

        if(!result.isPresent()){
            throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
        }

        Review review = result.get();

        review.setContent(patchUpdateReviewDtoReq.getContent());
        review.setStars(patchUpdateReviewDtoReq.getStars());
        review.setUpdatedAt(Timestamp.from(Instant.now()));

        review = reviewRepository.save(review);

        PatchUpdateReviewDtoRes response = PatchUpdateReviewDtoRes.builder()
                .content(review.getContent())
                .stars(review.getStars())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .orderedHouseId(review.getOrderedHouse().getId())
                .build();

        return response;
    }

    @Transactional(readOnly = false)
    public Boolean deleteReview(User user, Long reviewId){
        Optional<Review> result = reviewRepository.findByIdAndUserId(reviewId, user.getId());
        if (result.isPresent()){
            Review review = result.get();
            review.setStatus(false);
            reviewRepository.save(review);
            
            House house = review.getOrderedHouse().getCart().getHouse();
            house.decreaseReviewCount();
            houseRepository.save(house);

            return true;
        }
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }
}
