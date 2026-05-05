package com.tw.joi.delivery.dto.response;

import com.tw.joi.delivery.domain.GroceryProduct;

import java.util.List;

public record InventoryHealthReport(String storeId, List<ProductInventoryInfo> stockOutRiskProducts) {
    public record ProductInventoryInfo(String productId, String productName, int threshold, int availableStock) {
        public static ProductInventoryInfo fromEntity(GroceryProduct gp) {
            return new ProductInventoryInfo(gp.getProductId(), gp.getProductName(), gp.getThreshold(), gp.getAvailableStock());
        }
    }
}
