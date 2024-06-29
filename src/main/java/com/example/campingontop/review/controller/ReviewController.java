package com.example.campingontop.review.controller;

import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.model.response.PostCreateReviewDtoRes;
import com.example.campingontop.review.service.ReviewService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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
            description = "인증회원이 후기글을 생성할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/postreview")
    public ResponseEntity<PostCreateReviewDtoRes> createReview(@Valid @RequestBody User user, PostCreateReviewDtoReq postCreateReviewDtoReq) throws MessagingException {
        return ResponseEntity.ok().body(reviewService.createReview(user, postCreateReviewDtoReq));
    }

    @Operation(summary = "후기글 조회",
            description = "인증회원이 후기글을 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("find/{userId}")
    public ResponseEntity findReview (@Valid @Parameter(description = "조회할 user의 id") @PathVariable Long userId) {
        return ResponseEntity.ok().body(reviewService.findReviewByUserId(userId));
    }
}
