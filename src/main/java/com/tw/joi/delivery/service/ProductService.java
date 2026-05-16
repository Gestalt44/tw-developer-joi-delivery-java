package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.OutletInventory;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.dto.response.ProductResponse;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.repository.OutletIRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

  private OutletIRepository outletRepository;

  public List<ProductResponse> findProducts(long storeId) {
    //
    return outletRepository.findById(storeId).orElseThrow(
            () -> new JoiNotFoundException("Store"))
        .getInventory().stream()
        .map(OutletInventory::getProduct).map(
            product -> new ProductResponse(
                product.getName(), product.getBasePrice()))
        .toList();
  }
}
