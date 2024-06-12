package com.example.campingontop.likes.controller;

import com.example.campingontop.likes.model.dto.request.PostCreateLikesDtoReq;
import com.example.campingontop.likes.model.dto.response.GetLikesDtoRes;
import com.example.campingontop.likes.service.LikesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="Likes", description = "Likes 숙소 좋아요 CRUD")
@Api(tags = "Likes")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;

    @Operation(summary = "Likes 숙소 좋아요 생성",
            description = "숙소 좋아요 목록을 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/create")
    public ResponseEntity createLikes(
            @Valid @RequestBody PostCreateLikesDtoReq request
    ) {
        return ResponseEntity.ok().body(likesService.createLikes(request));
    }


    @Operation(summary = "Likes 조회",
            description = "유저 ID로 유저가 좋아요한 숙소 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/user/{userId}")
    public GetLikesDtoRes getLikesByUserId(@PathVariable Long userId) {
        return likesService.findLikesByUserId(userId);
    }


    @Operation(summary = "Likes 좋아요 삭제",
            description = "유저 ID로 유저가 좋아요한 숙소 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PatchMapping("/delete/{likesId}")
    public ResponseEntity deleteLikes(@PathVariable Long likesId) {
        likesService.deleteLikes(likesId);
        return ResponseEntity.ok().body("likes delete success");
    }
}
