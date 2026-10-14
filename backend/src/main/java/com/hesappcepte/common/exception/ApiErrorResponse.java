package com.hesappcepte.common.exception;

public record ApiErrorResponse(
        String code,
        String message
) {
}