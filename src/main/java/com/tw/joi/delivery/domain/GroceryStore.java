package com.tw.joi.delivery.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryStore extends Outlet {

    private Set<GroceryProduct> inventory = new HashSet<>();

    @Builder
    public GroceryStore(String name, String description, String outletId) {
        super(name, description, outletId);
        this.inventory = new HashSet<>();
    }

    public void addProduct(GroceryProduct product) {
        this.inventory.add(product);
    }
}
