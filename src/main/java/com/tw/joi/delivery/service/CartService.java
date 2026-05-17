package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.CartStatus;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartItemResponse;
import com.tw.joi.delivery.dto.response.CartResponse;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.repository.CartRepository;
import com.tw.joi.delivery.repository.OutletRepository;
import com.tw.joi.delivery.repository.ProductRepository;
import com.tw.joi.delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OutletRepository outletRepository;

  @Transactional // Guarantees database atomicity and handles dirty-checking automatically
  public void addItemToCart(AddProductRequest request) {

    // 1. Fetch the active cart or orchestrate a safe creation fallback path
    Cart cart = cartRepository.findByUserUserIdAndOutletOutletIdAndStatus(
            request.getUserId(), request.getOutletId(), CartStatus.ACTIVE)
        .orElseGet(() -> createNewCart(request.getUserId(), request.getOutletId()));

    // 2. Safely locate the targeted product resource
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new JoiNotFoundException(
            "Product not found with id: " + request.getProductId()));

    // 3. Delegate the operational state adjustment directly to the Aggregate Root
    cart.addOrUpdateItem(product, request.getQuantity());

    // No explicit cartRepository.save() needed here!
    // Since the entity is attached, @Transactional updates the DB automatically on method exit.
  }

  private Cart createNewCart(Long userId, Long outletId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new JoiNotFoundException("User not found with id: " + userId));
    Outlet outlet = outletRepository.findById(outletId)
        .orElseThrow(() -> new JoiNotFoundException("Outlet not found with id: " + outletId));

    Cart newCart = Cart.builder()
        .user(user)
        .outlet(outlet)
        .status(CartStatus.ACTIVE)
        .build();

    return cartRepository.save(newCart);
  }

  public CartResponse findActiveCart(Long userId, Long outletId) {

    return cartRepository
        .findByUserUserIdAndOutletOutletIdAndStatus(
            userId,
            outletId,
            CartStatus.ACTIVE)
        .map(cart -> {

          List<CartItemResponse> items =
              cart.getItems().stream()
                  .map(item -> {
                    Product product = item.getProduct();

                    double lineTotal =
                        product.getBasePrice() * item.getQuantity();

                    return new CartItemResponse(
                        product.getProductId(),
                        product.getName(),
                        product.getBasePrice(),
                        item.getQuantity(),
                        lineTotal
                    );
                  })
                  .toList();

          double cartTotal =
              items.stream()
                  .mapToDouble(CartItemResponse::lineTotal)
                  .sum();

          return new CartResponse(
              cart.getCartId(),
              cart.getUser().getUserId(),
              cart.getOutlet().getOutletId(),
              items,
              cartTotal
          );
        })
        .orElseGet(() ->
            new CartResponse(
                null,
                null,
                null,
                List.of(),
                null
            )
        );
  }

}
