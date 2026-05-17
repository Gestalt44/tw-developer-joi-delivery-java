package com.tw.joi.delivery.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
@Builder
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cartId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "outlet_id")
  private Outlet outlet;

  @Enumerated(EnumType.STRING)
  private CartStatus status;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<CartItem> items = new ArrayList<>();

  // Inside com.tw.joi.delivery.domain.Cart
  public void addOrUpdateItem(Product product, Integer quantity) {
    this.items.stream()
        .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
        .findFirst()
        .ifPresentOrElse(
            existingItem -> existingItem.setQuantity(existingItem.getQuantity() + quantity),
            () -> {
              CartItem newItem = CartItem.builder()
                  .cart(this)
                  .product(product)
                  .quantity(quantity)
                  .build();
              this.items.add(newItem);
            }
        );
  }
}