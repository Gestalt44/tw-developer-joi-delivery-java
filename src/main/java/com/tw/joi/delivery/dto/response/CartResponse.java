package com.tw.joi.delivery.dto.response;


import java.util.List;

public record CartResponse(
    Long cartId,
    Long userId,
    Long outletId,
    List<CartItemResponse> items,
    Double totalCartAmount
) {

}