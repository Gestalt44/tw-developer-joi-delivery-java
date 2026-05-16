package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
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
    public ResponseEntity<CartProductInfo> addItem(@RequestBody AddProductRequest request) {
        // Returning 201 Created is the standard for adding/creating items
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItemToCart(request));
    }

    // GET /carts?userId=123
    @GetMapping
    public ResponseEntity<Cart> getCartByUserId(@RequestParam String userId) {
        return ResponseEntity.ok(cartService.findActiveCartByUserId(userId));
    }
}