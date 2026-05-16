package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.dto.response.ProductResponse;
import com.tw.joi.delivery.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

  private ProductService productService;

  @GetMapping()
  ResponseEntity<List<ProductResponse>> viewAllProducts(
      @RequestParam long storeId) {
    return ResponseEntity.ok(
        productService.findProducts(storeId));
  }
}
