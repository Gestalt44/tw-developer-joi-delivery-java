package com.tw.joi.delivery.dto.response;

public record CartItemResponse(
    Long productId,
    String productName,
    Double price,
    Integer quantity,
    Double lineTotal
) {

}
