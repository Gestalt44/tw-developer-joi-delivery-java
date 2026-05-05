package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.request.DeleteProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.dto.response.DeleteProductResponse;
import com.tw.joi.delivery.exception.InvalidProductForStoreState;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.seedData.SeedData;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Map<String, Cart> userCarts = SeedData.cartForUsers;
    private final UserService userService;
    private final ProductService productService;

    public CartProductInfo addProductToCartForUser(AddProductRequest addProductRequest) {
        User user = userService.fetchUserById(addProductRequest.getUserId());
        Cart cart = fetchCartForUser(user);
        GroceryProduct product = productService.getProduct(addProductRequest.getProductId(),
                addProductRequest.getOutletId());
        if (null != product && !product.getStore().getOutletId().equals(cart.getOutlet().getOutletId())) {
            throw new InvalidProductForStoreState(addProductRequest.getProductId(), addProductRequest.getOutletId(), cart.getOutlet().getOutletId());
        } else if (null == product) {
            throw new JoiNotFoundException("For given store,Product");
        }
        cart.getProducts().add(product);
        return new CartProductInfo(cart, product, product.getSellingPrice());
    }

    public Cart getCartForUser(String userId) {
        User user = userService.fetchUserById(userId);
        return fetchCartForUser(user);
    }

    private Cart fetchCartForUser(User user) {
        return userCarts.get(user.getUserId());
    }

    public DeleteProductResponse deleteProductFromCart(DeleteProductRequest req) {
        Cart cart = getCartForUser(req.userId());
        if (cart == null) throw new JoiNotFoundException("Cart");

        boolean removed = cart.getProducts().removeIf(p -> p.getProductId().equals(req.productId()));

        if (!removed) {
            throw new JoiNotFoundException("Product not in cart");
        }

        return new DeleteProductResponse("Deleted successfully");
    }

}
