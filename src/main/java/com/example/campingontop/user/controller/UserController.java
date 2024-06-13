package com.example.campingontop.user.controller;

import com.example.campingontop.user.model.request.PostCreateUserDtoReq;
import com.example.campingontop.user.model.request.PostEmailConfirmDtoReq;
import com.example.campingontop.user.model.request.PostLoginUserDtoReq;
import com.example.campingontop.user.model.request.PutUpdateUserDtoReq;
import com.example.campingontop.user.model.response.*;
import com.example.campingontop.user.service.EmailVerifyService;
import com.example.campingontop.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@Tag(name="User", description = "User CRUD")
@Api(tags = "User")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final EmailVerifyService emailVerifyService;


    @Operation(summary = "User 일반 유저 회원가입",
            description = "일반 유저의 회원가입을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/signUp")
    public ResponseEntity<PostSignUpUserDtoRes> signUpUser(@Valid @RequestBody PostCreateUserDtoReq postCreateUserDtoReq) throws MessagingException {
        return ResponseEntity.ok().body(userService.signUpUser(postCreateUserDtoReq));
    }


    @Operation(summary = "User 판매자 유저 회원가입",
            description = "판매자 유저의 회원가입을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/sellerSignUp")
    public ResponseEntity signUpSeller(@Valid @RequestBody PostCreateUserDtoReq postCreateUserDtoReq) throws MessagingException {
        return ResponseEntity.ok().body(userService.signUpSeller(postCreateUserDtoReq));
    }


    @Operation(summary = "User 로그인",
            description = "회원가입한 유저의 로그인을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping( "/login")
    public ResponseEntity login(@Valid @RequestBody PostLoginUserDtoReq req) {
        return ResponseEntity.ok().body(userService.login(req));
    }


    @Operation(summary = "User 이메일 인증",
            description = "회원가입 과정에서 이메일 검증을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping( "/verify")
    public RedirectView confirm(@Valid @ModelAttribute PostEmailConfirmDtoReq req) {
        if (emailVerifyService.verify(req)) {
            userService.updateMemberStatus(req.getEmail());
//            return new RedirectView("http://www.campingontop.kro.kr/");
            return new RedirectView("http://localhost:8081/");
        } else {
//            return new RedirectView("http://www.campingontop.kro.kr/email/verify");
            return new RedirectView("http://localhost:8081/email/verify");
        }
    }

    @Operation(summary = "이메일 micro service를 통한 User 이메일 인증",
            description = "회원가입 과정에서 micro service에 요청을 통한 이메일 검증을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping( "/confirmEmail")
    public ResponseEntity confirmEmail(@RequestParam String email, @RequestParam String uuid) {
        return ResponseEntity.ok().body(emailVerifyService.verifyMsa(email, uuid));
    }


    @Operation(summary = "User 유저 단일 조회",
            description = "유저 ID로 유저 1명을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/find/{userId}")
    public ResponseEntity findUserById(@Valid @Parameter(description = "조회할 user의 id") @PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findUserById(userId));
    }


    @Operation(summary = "User 유저 전체 목록 조회",
            description = "전체 유저들의 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("find/list")
    public ResponseEntity findUserList() {
        return ResponseEntity.ok().body(userService.findUserList());
    }


    @Operation(summary = "User 유저 정보 수정",
            description = "유저의 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@Valid @RequestBody PutUpdateUserDtoReq putUpdateUserDtoReq, @PathVariable Long userId) {
        PutUpdateUserDtoRes user = userService.updateUser(putUpdateUserDtoReq, userId);
        return ResponseEntity.ok().body(user);
    }


    @Operation(summary = "User 유저 삭제",
            description = "유저 ID로 유저 데이터 1개를 삭제하는 API입니다. (status: true -> false 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PutMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@Valid @Parameter(description = "삭제할 user의 id") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User 회원 탈퇴 완료");
    }
}
