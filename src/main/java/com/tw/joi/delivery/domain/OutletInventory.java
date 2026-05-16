package com.tw.joi.delivery.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"outlet_id", "product_id"}
                )
        }
)
public class OutletInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletInventoryId;

    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer stock;

    private Double sellingPrice;

}