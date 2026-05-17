package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.AddCartItemResponse;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.dto.response.CartResponse;
import com.tw.joi.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts") // Plural resource
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  // POST /carts/items
  @PostMapping("/items")
  public ResponseEntity<Void> addItem(@RequestBody AddProductRequest request) {
    // Returning 201 Created is the standard for adding/creating items
    cartService.addItemToCart(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<CartResponse> getActiveCart(
      @RequestParam Long userId,
      @RequestParam Long outletId) {
    return ResponseEntity.ok(cartService.findActiveCart(userId, outletId));
  }
}