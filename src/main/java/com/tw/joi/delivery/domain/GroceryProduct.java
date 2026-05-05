package com.tw.joi.delivery.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryProduct extends Product {

    private BigDecimal weight;

    private int expiryDate;

    private int threshold;

    private int availableStock;

    private BigDecimal discount;
    @JsonIgnore
    private GroceryStore store;

    @Builder
    public GroceryProduct(String productId, String productName, BigDecimal mrp, Cart cart, BigDecimal weight, int expiryDate, int threshold,
                          int availableStock, GroceryStore store, BigDecimal discount) {
        super(productId, productName, mrp);
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.threshold = threshold;
        this.availableStock = availableStock;
        this.store = store;
        this.discount = discount;
    }

    public BigDecimal getSellingPrice() {
        BigDecimal convertedDiscount = discount.divide(BigDecimal.valueOf(100));
        return mrp.subtract(mrp.multiply(convertedDiscount));
    }
}
