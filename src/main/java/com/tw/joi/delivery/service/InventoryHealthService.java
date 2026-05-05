package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.dto.response.InventoryHealthReport;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryHealthService {
    public InventoryHealthReport fetchInventoryHealthReport(String storeId) {
        var store = SeedData.stores.get(storeId);

        if (null == store) {
            throw new JoiNotFoundException("Store " + storeId);
        }

        // Handle null inventory gracefully
        var inventory = store.getInventory() != null ? store.getInventory() : Set.<GroceryProduct>of();

        var atRiskProducts = inventory.stream()
                .filter(p -> p.getAvailableStock() <= p.getThreshold())
                .map(InventoryHealthReport.ProductInventoryInfo::fromEntity) // Use a static factory
                .collect(Collectors.toList());

        return new InventoryHealthReport(storeId, atRiskProducts);
    }
}
