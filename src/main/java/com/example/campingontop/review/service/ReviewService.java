package com.example.campingontop.review.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrdersException;
import com.example.campingontop.exception.entityException.ReviewException;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.orders.repository.OrdersRepository;
import com.example.campingontop.review.model.request.GetReviewListPagingByLocReq;
import com.example.campingontop.review.model.request.PatchUpdateReviewDtoReq;
import com.example.campingontop.review.model.response.GetFindReviewDtoRes;
import com.example.campingontop.review.model.response.PatchUpdateReviewDtoRes;
import com.example.campingontop.review.model.response.PostCreateReviewDtoRes;
import com.example.campingontop.reviewImage.model.ReviewImage;
import com.example.campingontop.reviewImage.service.ReviewImageService;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.repository.ReviewRepository;
import com.example.campingontop.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final OrdersRepository ordersRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;

    @Transactional
    public PostCreateReviewDtoRes createReview(User user, PostCreateReviewDtoReq request, MultipartFile[] images) {
        Optional<Orders> result = ordersRepository.findById(request.getOrdersId());
        if (result.isPresent()) {
            Orders orders = result.get();

            Review review = Review.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .rating(request.getRating())
                    .orders(orders)
                    .status(true)
                    .build();
            review = reviewRepository.save(review);

            reviewImageService.createImage(review.getId(), images);
            PostCreateReviewDtoRes response = PostCreateReviewDtoRes.toDto(review);

            return response;
        }
        throw new OrdersException(ErrorCode.ORDERS_NOT_EXIST);
    }


    @Transactional(readOnly = true)
    public GetFindReviewDtoRes findReviewById(Long reviewId) {
        Review result = reviewRepository.findReviewById(reviewId);
        if (result != null && result.getStatus() != false) {
            Review review = result;
            List<ReviewImage> reviewImageList = review.getReviewImageList();

            List<String> filenames = new ArrayList<>();

            for (ReviewImage reviewImage : reviewImageList) {
                String filename = reviewImage.getFilename();
                filenames.add(filename);
            }

            GetFindReviewDtoRes res = GetFindReviewDtoRes.toDto(review, filenames);
            return res;
        }
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public List<GetFindReviewDtoRes> findReviewList(GetReviewListPagingByLocReq req) {
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<Review> result = reviewRepository.findList(pageable);

        int ratingAvg = 0;
        int ratingSum = 0;
        for (Review review : result) {
            ratingSum += review.getRating();
        }
        ratingAvg = ratingSum / result.getSize();

        List<GetFindReviewDtoRes> reviewList = new ArrayList<>();

        for (Review review : result.getContent()) {
            List<ReviewImage> reviewImageList = review.getReviewImageList();

            List<String> filenames = new ArrayList<>();
            for (ReviewImage productImage : reviewImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindReviewDtoRes res = GetFindReviewDtoRes.toDto(review, filenames, ratingAvg);
            reviewList.add(res);
        }
        return reviewList;
    }

    public PatchUpdateReviewDtoRes updateReview(User user, PatchUpdateReviewDtoReq req, Long reviewId) {
        Optional<Review> result = reviewRepository.findById(reviewId);
        if (result.isPresent()) {
            Review review = result.get();

            review.setTitle(req.getTitle());
            review.setContent(req.getContent());
            review.setRating(req.getRating());

            review = reviewRepository.save(review);

            PatchUpdateReviewDtoRes res = PatchUpdateReviewDtoRes.toDto(review);
            return res;
        }
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }

    public void deleteReview(User user, Long reviewId) {
        Optional<Review> result = reviewRepository.findActiveReviewForDelete(user, reviewId);
        if (result.isPresent()) {
            Review review = result.get();
            review.setStatus(false);
            reviewRepository.save(review);
            return;
        }
        throw new ReviewException(ErrorCode.REVIEW_NOT_EXIST);
    }
}
