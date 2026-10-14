package com.hesappcepte.common.exception;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException exception){

        ErrorCode errorCode = exception.getErrorCode();

        ApiErrorResponse response = new ApiErrorResponse(
                errorCode.name(),
                errorCode.getMessage()
        );
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.VALIDATION_ERROR.name(),
                message
        );

        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(response);
    }

}
