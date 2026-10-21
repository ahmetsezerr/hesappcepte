package com.hesappcepte.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    EMAIL_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "Email address is already registered."
    ),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "Request validation failed."
    ),

    INVALID_CREDENTIALS(

            HttpStatus.UNAUTHORIZED,
        "Invalid email or password."
    ),

    USER_DISABLED(
            HttpStatus.FORBIDDEN,
            "User account is disabled."
    );

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}