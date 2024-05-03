package com.example.campingontop.review.controller;

import com.example.campingontop.review.model.request.GetReviewListPagingByLocReq;
import com.example.campingontop.review.model.request.PatchUpdateReviewDtoReq;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.model.response.GetFindReviewDtoRes;
import com.example.campingontop.review.model.response.PatchUpdateReviewDtoRes;
import com.example.campingontop.review.repository.ReviewRepository;
import com.example.campingontop.review.service.ReviewService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

@Tag(name="Review", description = "Review 숙소 리뷰 CRUD")
@Api(tags = "Review")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "review 등록",
            description = "리뷰를 등록하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/create")
    public ResponseEntity createReview(
            @AuthenticationPrincipal User user,
            @RequestPart PostCreateReviewDtoReq postCreateReviewDtoReq,
            @RequestPart MultipartFile[] uploadFiles
    ) {
        return ResponseEntity.ok().body(reviewService.createReview(user, postCreateReviewDtoReq, uploadFiles));
    }


    @Operation(summary = "Review 리뷰 조회",
            description = "리뷰 ID로 리뷰 1개를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/find/{reviewId}")
    public ResponseEntity findReviewById(
            @Parameter(description = "조회할 review의 id") @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok().body(reviewService.findReviewById(reviewId));
    }


    @Operation(summary = "Review 리뷰 목록 조회",
            description = "전체 리뷰들의 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/list")
    public ResponseEntity findReviewList(GetReviewListPagingByLocReq req) {
        List<GetFindReviewDtoRes> reviewList = reviewService.findReviewList(req);
        return ResponseEntity.ok().body(reviewList);
    }


    @Operation(summary = "Review 리뷰 정보 수정",
            description = "리뷰의 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PatchMapping("/update/{reviewId}")
    public ResponseEntity updateReview(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PatchUpdateReviewDtoReq patchUpdateReviewDtoReq,
            @PathVariable Long reviewId
    ) {
        PatchUpdateReviewDtoRes review = reviewService.updateReview(user, patchUpdateReviewDtoReq, reviewId);
        return ResponseEntity.ok().body(review);
    }


    @Operation(summary = "Review 리뷰 삭제",
            description = "리뷰 ID로 리뷰 데이터 1개를 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PatchMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(
            @AuthenticationPrincipal User user,
            @Parameter(description = "삭제할 review의 id") @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity.ok().body("Review delete success");
    }
}
