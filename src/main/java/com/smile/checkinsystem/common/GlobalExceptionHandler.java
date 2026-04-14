package com.smile.checkinsystem.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Object> handleRuntimeException(RuntimeException exception) {
        return ApiResponse.error(400, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(Exception exception) {
        return ApiResponse.error(500, "服务器内部错误");
    }
}
