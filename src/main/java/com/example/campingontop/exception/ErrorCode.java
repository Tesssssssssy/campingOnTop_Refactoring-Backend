package com.example.campingontop.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 공통 에러 코드
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 잘못되었습니다.."),

    // User 관련 에러 코드
    DUPLICATED_USER(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
    MEMBER_NOT_EXIST(HttpStatus.CONFLICT, "존재하지 않는 사용자입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 인증을 하지 않았습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "사용자 인증 실패"),



    // House 관련 에러 코드
    DUPLICATED_HOUSE(HttpStatus.CONFLICT, "중복된 숙소 이름이 존재합니다."),
    HOUSE_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 숙소입니다."),
    IMAGE_EMPTY(HttpStatus.UNAUTHORIZED, "사진을 첨부해주세요."),
    DUPLICATED_HOUSE_NAME(HttpStatus.CONFLICT, "중복된 숙소 이름이 존재합니다."),


    // Cart 관련 에러 코드
    DUPLICATED_RESERVATION(HttpStatus.CONFLICT, "이미 예약 완료된 숙소입니다."),
    CART_EMPTY(HttpStatus.NOT_FOUND, "장바구니가 비어있습니다"),
    CART_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 숙소는 장바구니에 존재하지 않습니다."),


    // Like 관련 에러 코드
    DUPLICATED_LIKES(HttpStatus.CONFLICT, "이미 좋아요 목록에 존재하는 숙소입니다."),
    LIKES_NOT_EXIST(HttpStatus.NOT_FOUND, "좋아요 목록에 추가한 숙소가 없습니다."),


    // OrderedHouse 관련 에러
    ORDEREDHOUSE_NOT_EXIST(HttpStatus.NOT_FOUND, "숙소 결제 내역이 없습니다."),


    // Review 관련 에러
    UNAVAILABLE_WRITE_REVIEW(HttpStatus.UNAUTHORIZED, "해당 숙소의 결제 내역이 존재하지 않아 리뷰 작성이 불가능합니다."),

    REVIEW_NOT_EXIST(HttpStatus.NOT_FOUND,"해당 리뷰가 존재하지 않습니다."),


    // Orders 관련 에러
    ORDERS_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 주문 내역이 존재하지 않습니다."),
    ;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private final HttpStatus status;
    private final String message;
}
