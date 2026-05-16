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
                        columnNames = {"order_id", "product_id"}
                )
        }
)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    /**
     * Snapshot value at purchase time.
     */
    private Double purchasedPrice;
}