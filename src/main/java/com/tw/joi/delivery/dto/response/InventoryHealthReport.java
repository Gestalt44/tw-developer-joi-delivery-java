package com.tw.joi.delivery.dto.response;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

public record InventoryHealthReport(
        String outletId,
        Set<ProductInventoryInfo> itemsAtRisk
) {
    public record ProductInventoryInfo(
            String productId,
            String productName,
            int threshold,
            int availableStock
    ) {
        public static ProductInventoryInfo fromEntity(GroceryProduct p) {
            return new ProductInventoryInfo(p.getProductId(), p.getProductName(), p.getThreshold(), p.getAvailableStock());
        }
    }
}