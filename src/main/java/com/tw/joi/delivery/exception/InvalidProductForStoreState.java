package com.tw.joi.delivery.exception;

public class InvalidProductForStoreState extends RuntimeException {
    String productId;
    String requestedStoreId;
    String cartStoreId;

    public InvalidProductForStoreState(String productId, String requestedStoreId, String cartStoreId) {
        this.productId = productId;
        this.requestedStoreId = requestedStoreId;
        this.cartStoreId = cartStoreId;
    }
}
