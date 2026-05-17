package com.tw.joi.delivery.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {

  private Long userId;
  private Long outletId;
  private Long productId;
  private Integer quantity;
}
