package com.tw.joi.delivery.exception;

import com.tw.joi.delivery.dto.response.JoiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JoiNotFoundException.class)
    public ResponseEntity<JoiErrorResponse> notFoundExceptionHandler(JoiNotFoundException ex) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new JoiErrorResponse(ex.getMessage(), null, null, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JoiErrorResponse> notFoundExceptionHandler(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new JoiErrorResponse("Internal server error", null, null, null));
    }
}
