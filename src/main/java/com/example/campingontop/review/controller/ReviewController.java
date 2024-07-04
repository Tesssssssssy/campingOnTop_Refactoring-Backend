package com.example.campingontop.review.controller;

import com.example.campingontop.review.model.request.PatchUpdateReviewDtoReq;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="Review", description = "Review CRUD")
@Api(tags = "Review")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "후기글 생성",
            description = "회원이 후기글을 생성할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/create")
    public ResponseEntity createReview(@Valid @RequestBody PostCreateReviewDtoReq postCreateReviewDtoReq) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(reviewService.createReview(user, postCreateReviewDtoReq));
    }

    @Operation(summary = "본인이 쓴 후기글 조회",
            description = "회원이 본인이 쓴 후기글을 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/mylist")
    public ResponseEntity findReview () {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(reviewService.findReviewByUserId(user));
    }

    @Operation(summary = "숙소에 달린 후기글 조회",
            description = "숙소에 달린 후기글을 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/find/{houseId}")
    public ResponseEntity readReview (@Parameter(description = "조회할 house의 id") @PathVariable Long houseId) {
        return ResponseEntity.ok().body(reviewService.findReviewByHouseId(houseId));
    }

    @Operation(summary = "후기글 수정",
            description = "인증회원이 후기글을 수정할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PatchMapping("/update")
    public ResponseEntity updateReview (@Valid @RequestBody PatchUpdateReviewDtoReq patchUpdateReviewDtoReq) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(reviewService.updateReview(user,patchUpdateReviewDtoReq));
    }

    @Operation(summary = "후기글 삭제",
            description = "인증회원이 후기글을 삭제할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PatchMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@Valid @Parameter(description = "삭제할 Review의 id") @PathVariable Long reviewId){
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(reviewService.deleteReview(user, reviewId));
    }

}
