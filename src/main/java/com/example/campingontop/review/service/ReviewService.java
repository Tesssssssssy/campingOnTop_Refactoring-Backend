package com.example.campingontop.review.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrderedHouseException;
import com.example.campingontop.exception.entityException.ReviewException;
import com.example.campingontop.orders.model.OrderedHouse;
import com.example.campingontop.orders.repository.OrderedHouseRespository;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.request.PatchUpdateReviewDtoReq;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.model.response.GetFindReviewByUserIdDtoRes;
import com.example.campingontop.review.model.response.GetFindReviewByUserIdDtoResResult;
import com.example.campingontop.review.model.response.PostCreateReviewDtoRes;
import com.example.campingontop.review.repository.ReviewRepository;
import com.example.campingontop.user.model.User;
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

    @Transactional(readOnly = false)
    public PostCreateReviewDtoRes createReview (User user, PostCreateReviewDtoReq postCreateReviewDtoReq){
        List<Review> searchReview = reviewRepository.findReviewByOrderedHouseId(postCreateReviewDtoReq.getOrdersedHouseId());
        OrderedHouse orderedHouse = orderedHouseRespository.findById(postCreateReviewDtoReq.getOrdersedHouseId())
                .orElseThrow(() -> new OrderedHouseException(ErrorCode.ORDEREDHOUSE_NOT_EXIST));

        if (!searchReview.isEmpty()){
            throw new ReviewException(ErrorCode.DUPLICATE_REVIEW);
        }{
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

            return postCreateReviewDtoRes;
        }

    }

    @Transactional(readOnly = true)
    public GetFindReviewByUserIdDtoResResult findReviewByUserId(Long userId){
        List<Review> list = reviewRepository.findReviewByUserId(userId);
        List<GetFindReviewByUserIdDtoRes> getFindReviewByUserIdDtoResList = new ArrayList<>();

        if(!list.isEmpty()){
            for(Review review : list){
                GetFindReviewByUserIdDtoRes getFindReviewByUserIdDtoRes = GetFindReviewByUserIdDtoRes.builder()
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
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public void findReviewByHouseId(Long houseId){

    }

    public void updateReview(User user, PatchUpdateReviewDtoReq patchUpdateReviewDtoReq, Long reviewId){
        Optional<Review> result = reviewRepository.findByIdxAndUserIdx(patchUpdateReviewDtoReq.getReviewId(), user.getId());

        if(!result.isPresent()){
            throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
        }

        Review review = result.get();

        review = Review.builder()
                .content(patchUpdateReviewDtoReq.getContent())
                .stars(patchUpdateReviewDtoReq.getStars())
                .updatedAt(Timestamp.from(Instant.now()))
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public Boolean deleteReview(Long reviewId){
        Optional<Review> result = reviewRepository.findById(reviewId);
        if (result.isPresent()){
            Review review = result.get();
            review.setStatus(false);
            reviewRepository.save(review);

            return true;
        }
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }
}
