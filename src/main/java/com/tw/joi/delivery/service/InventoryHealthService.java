package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.dto.response.InventoryHealthReport;
import com.tw.joi.delivery.seedData.InMemoryDataStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryHealthService {
    InMemoryDataStore dataRepo;

    public InventoryHealthReport fetchInventoryReport(String storeId) {
        var store = dataRepo.findStoreById(storeId)
                .orElseThrow(() -> new JoiNotFoundException("Store " + storeId));

        // Handle null inventory gracefully
        var inventory = store.getInventory() != null ? store.getInventory() : Set.<GroceryProduct>of();

        var atRiskProducts = inventory.stream()
                .filter(p -> p.getAvailableStock() <= p.getThreshold())
                .map(InventoryHealthReport.ProductInventoryInfo::fromEntity) // Use a static factory
                .collect(Collectors.toSet());

        return new InventoryHealthReport(storeId, atRiskProducts);
    }
}
