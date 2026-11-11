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
    ),
    ACCOUNT_NOT_FOUND
            (HttpStatus.NOT_FOUND,
            "Account not found."
            ),
    USER_NOT_FOUND
            (HttpStatus.NOT_FOUND,
                    "User not found."
            ),
    FINANCEAREA_NOT_FOUND
            (HttpStatus.NOT_FOUND,
                    "FinanceArea not found."
            ),
    FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "You do not have permission to perform this action."
    ),
    FINANCEAREA_ARCHIVED(
            HttpStatus.CONFLICT,
            "Archived finance areas cannot be updated."
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