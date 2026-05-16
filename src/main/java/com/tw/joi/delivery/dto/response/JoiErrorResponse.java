package com.tw.joi.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record JoiErrorResponse(String msg,
                               String userId,
                               String storeId,
                               String productId) {
}