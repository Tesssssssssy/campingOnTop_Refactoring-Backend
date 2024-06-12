package com.example.campingontop.exception;

import com.example.campingontop.exception.entityException.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionAdvise extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvise.class);

    @ExceptionHandler(UserException.class)
    public ResponseEntity handleUserException(UserException e) {
        log.error("UserException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(HouseException.class)
    public ResponseEntity handleHouseException(HouseException e) {
        log.error("HouseException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(LikesException.class)
    public ResponseEntity handleLikeException(LikesException e) {
        log.error("LikeException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity handleCartException(CartException e) {
        log.error("CartException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(OrdersException.class)
    public ResponseEntity handleOrdersException(OrdersException e) {
        log.error("OrdersException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return makeResponseEntity(ErrorCode.INVALID_INPUT);
    }

    private ResponseEntity makeResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus()).body(
                ErrorResponse.builder()
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
