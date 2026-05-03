package com.tw.joi.delivery.service;

import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.InMemoryDataStore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    InMemoryDataStore datarepo;

    public GroceryProduct getProduct(String productId, String outletId) {
        return datarepo.findProductById(productId, outletId).orElseThrow(() -> new JoiNotFoundException("User"));
    }

}
