package com.example.campingontop.user.model.request;

import com.example.campingontop.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostCreateUserDtoReq {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @ApiParam(value = "이메일", required = true, example = "test01@naver.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,20}$", message = "1~20글자의 한글, 영어, 숫자로 이루어진 name")
    @ApiParam(value = "닉네임", required = true, example = "nickname")
    private String name;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,20}$", message = "1~20글자의 한글, 영어, 숫자로 이루어진 name")
    @ApiParam(value = "닉네임", required = true, example = "nickname")
    private String nickName;


    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    @ApiParam(value = "핸드폰 번호", required = true, example = "010-1111-2222")
    private String phoneNum;

    @ApiParam(value = "성별", example = "M | F", required = false)
    private String gender;


    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    @ApiParam(value = "생년월일", required = true, example = "2023-12-29")
    private String birthDay;
}
