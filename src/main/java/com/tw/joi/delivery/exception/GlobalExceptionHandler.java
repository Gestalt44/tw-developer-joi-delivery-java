package com.tw.joi.delivery.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProductForStoreState.class)
    ResponseEntity<String> handleInvalidProductState(InvalidProductForStoreState e) {
        return ResponseEntity.badRequest().body(String.format("%s product not present in requested store %s because current store is %s", e.productId, e.requestedStoreId, e.cartStoreId));
    }

    @ExceptionHandler(JoiNotFoundException.class)
    ResponseEntity<String> handleInvalidProductState(JoiNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
