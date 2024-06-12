package com.example.campingontop.exception.entityException;

import com.example.campingontop.exception.ErrorCode;

public class ReviewException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public ReviewException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        } else {
            return String.format("[%s] %s", message, errorCode.getMessage());
        }

    }
}
