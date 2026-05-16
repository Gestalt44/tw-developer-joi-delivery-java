package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.CartStatus;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;

import com.tw.joi.delivery.repository.CartRepository;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  CartRepository cartRepository;

  public CartProductInfo addItemToCart(AddProductRequest request) {
    cartRepository.findByUserUserIdAndOutletOutletIdAndStatus(request.getUserId(),
        request.getOutletId(), CartStatus.ACTIVE);

    return null;
  }

  public Cart findActiveCartByUserId(String userId) {
    return null;
  }
}
