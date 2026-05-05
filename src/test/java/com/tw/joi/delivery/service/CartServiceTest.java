package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.*;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.request.DeleteProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.dto.response.DeleteProductResponse;
import com.tw.joi.delivery.exception.InvalidProductForStoreState;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {
    UserService userService;
    ProductService productService;
    CartService cartService;

    @BeforeEach
    void init() {
        userService = mock(UserService.class);
        productService = mock(ProductService.class);

        cartService = new CartService(userService, productService);

    }

    @Test
    void shouldThrowInvalidProductForStateException() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("product101");
        addProductRequest.setUserId("user101");
        addProductRequest.setOutletId("store101");

        User u1 = User.builder().userId("user101").build();
        GroceryStore g1 = GroceryStore.builder().outletId("store101").build();
        GroceryStore g2 = GroceryStore.builder().outletId("store102").build();

        GroceryProduct p1 = GroceryProduct.builder().store(g2).build();

        //Cart c1 = new Cart("cart101", g1, new ArrayList<>(), u1);
        when(userService.fetchUserById(addProductRequest.getUserId())).thenReturn(u1);
        //when(cartService.fetchCartForUser(u1)).thenReturn(c1);
        when(productService.getProduct(addProductRequest.getProductId(), addProductRequest.getOutletId())).thenReturn(p1);

        //act
        assertThrows(InvalidProductForStoreState.class, () -> cartService.addProductToCartForUser(addProductRequest));

    }

    @Test
    void shouldThrowJoiNotFoundExceptionWhenProductIsInvalid() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("product101");
        addProductRequest.setUserId("user101");
        addProductRequest.setOutletId("store101");

        User u1 = User.builder().userId("user101").build();
//        GroceryStore g1 = GroceryStore.builder().outletId("store102").build();
//        GroceryProduct p1 = GroceryProduct.builder().store(g1).build();
        when(userService.fetchUserById(addProductRequest.getUserId())).thenReturn(u1);
        //when(cartService.getCartForUser(addProductRequest.getUserId())).thenReturn(null);
        when(productService.getProduct(addProductRequest.getProductId(), addProductRequest.getOutletId())).thenReturn(null);

        //act
        assertThrows(JoiNotFoundException.class, () -> cartService.addProductToCartForUser(addProductRequest));

    }

    @Test
    void shouldCorrectlyCalculateDiscount() {
        User u1 = User.builder().userId("user101").build();
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("product101");
        addProductRequest.setUserId("user101");
        addProductRequest.setOutletId("store101");
        GroceryStore g1 = GroceryStore.builder().outletId("store101").build();

        GroceryProduct p1 = GroceryProduct.builder().store(g1).mrp(BigDecimal.valueOf(100)).discount(BigDecimal.valueOf(4)).build();
        when(userService.fetchUserById(any())).thenReturn(u1);
        when(productService.getProduct(anyString(), anyString())).thenReturn(p1);
        CartProductInfo result = cartService.addProductToCartForUser(addProductRequest);
        BigDecimal actualPrice = ((GroceryProduct) result.product()).getSellingPrice();
        assertEquals(0, BigDecimal.valueOf(96).compareTo(actualPrice));
    }

    @Test
    void shouldDeleteProductFromCart() {
        List<Product> products = new ArrayList<>();
        products.add(GroceryProduct.builder().productId("P1").build());
        products.add(GroceryProduct.builder().productId("P3").build());
        Cart c1 = Cart.builder().products(products).build();
        User u1 = User.builder().userId("userTest").build();
        Map<String, Cart> testCartMap = new HashMap<>();
        testCartMap.put("userTest", c1);
        ReflectionTestUtils.setField(cartService, "userCarts", testCartMap);
        when(userService.fetchUserById(any())).thenReturn(u1);

        //when(cartService.getCartForUser(u1.getUserId())).thenReturn(c1);
        int sizeBefore = c1.getProducts().size();
        DeleteProductRequest request = new DeleteProductRequest("P1", "user101");
        DeleteProductResponse response = cartService.deleteProductFromCart(request);
        assertEquals(sizeBefore - 1, c1.getProducts().size());
    }
}
