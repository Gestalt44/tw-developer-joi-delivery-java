package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.seedData.InMemoryDataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private UserService userService;
    private ProductService productService;
    private CartService cartService;
    private InMemoryDataStore dataRepo;

    @BeforeEach
    void setUp() {
        // 1. Manually create mocks
        userService = mock(UserService.class);
        productService = mock(ProductService.class);
        dataRepo = mock(InMemoryDataStore.class);
        // 2. Initialize service with mocks
        cartService = new CartService(dataRepo, userService, productService);
    }

    @Test
    void shouldAddProductSuccessfully() {
        // Arrange
        String userId = "user101";
        User u1 = new User();
        u1.setUserId(userId);

        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>()); // Fresh list for this test
        u1.setCart(cart);

        GroceryProduct gp = new GroceryProduct();
        gp.setProductId("p1");
        gp.setMrp(BigDecimal.valueOf(100));
        gp.setDiscount(BigDecimal.TEN.divide(BigDecimal.valueOf(100)));

        AddProductRequest req = new AddProductRequest();
        req.setUserId(userId);
        req.setProductId("p1");
        req.setOutletId("s1");

        // Set up the "Lies" (Mocks)
        when(userService.fetchUserById(userId)).thenReturn(u1);
        when(productService.getProduct("p1", "s1")).thenReturn(gp);
        when(dataRepo.findCartByUserId(userId)).thenReturn(Optional.of(cart));
        // Act
        CartProductInfo result = cartService.addProductToCartForUser(req);

        // Assert
        assertEquals(1, result.cart().getProducts().size());
        assertEquals("p1", result.cart().getProducts().get(0).getProductId());
        // Check if the selling price was calculated correctly (mrp - discount)
        assertEquals(BigDecimal.valueOf(90.0), result.sellingPrice());
    }
}