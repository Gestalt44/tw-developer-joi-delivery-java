package com.tw.joi.delivery.seedData;

import com.tw.joi.delivery.domain.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * InMemoryDataStore acts as a "Virtual Database."
 * It ensures referential integrity during initialization and provides
 * O(1) lookups for the Service layer.
 */
@Component
public class InMemoryDataStore {

    // Maps for O(1) point lookups
    private final Map<String, GroceryStore> stores = new ConcurrentHashMap<>();
    private final List<GroceryProduct> products = new CopyOnWriteArrayList<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, Cart> cartsByUserId = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 1. Create Stores
        GroceryStore store101 = registerStore("store101", "Fresh Picks", "Quality groceries at your doorstep.");
        GroceryStore store102 = registerStore("store102", "Natural Choice", "Organic and fresh produce.");

        BigDecimal discount = BigDecimal.TEN.divide(BigDecimal.valueOf(100));
        // 2. Create Products and link to Stores
        registerProduct("product101", "Wheat Bread", BigDecimal.valueOf(10.5), BigDecimal.valueOf(500), store101, discount, 10, 30);
        registerProduct("product102", "Spinach", BigDecimal.valueOf(5.0), BigDecimal.valueOf(200), store101, discount, 9, 6);
        registerProduct("product103", "Crackers", BigDecimal.valueOf(3.5), BigDecimal.valueOf(150), store101, discount, 15, 22);

        registerProduct("product201", "Organic Milk", BigDecimal.valueOf(12.0), BigDecimal.valueOf(1000), store102, discount, 12, 22);

        // 3. Create Users
        User user101 = registerUser("user101", "John", "Doe", "555-0101");
        User user102 = registerUser("user102", "Rachel", "Zane", "555-0102");

        // 4. Create Carts and link to Users & Stores
        // A Cart needs a User and a default Outlet
        registerCart("cart101", user101, store101);
        registerCart("cart102", user102, store102);
    }

    // --- Registration Helpers (Referential Integrity Enforcement) ---

    private GroceryStore registerStore(String id, String name, String desc) {
        GroceryStore store = new GroceryStore(name, desc, id);
        stores.put(id, store);
        return store;
    }

    private void registerProduct(String id, String name, BigDecimal mrp, BigDecimal weight, GroceryStore store, BigDecimal discount, int threshold, int availableStock) {
        GroceryProduct product = GroceryProduct.builder()
                .productId(id)
                .productName(name)
                .mrp(mrp)
                .weight(weight)
                .store(store) // Link Product -> Store
                .threshold(threshold)
                .availableStock(availableStock)
                .discount(discount)
                .build();

        products.add(product);
        store.getInventory().add(product);
    }

    private User registerUser(String id, String first, String last, String phone) {
        User user = User.builder()
                .userId(id)
                .firstName(first)
                .lastName(last)
                .email(first.toLowerCase() + "." + last.toLowerCase() + "@gmail.com")
                .phoneNumber(phone)
                .build();
        users.put(id, user);
        return user;
    }

    private void registerCart(String cartId, User user, GroceryStore outlet) {
        Cart cart = Cart.builder()
                .cartId(cartId)
                .user(user)   // Link Cart -> User
                .outlet(outlet)
                .products(new ArrayList<>())
                .build();

        // Link User -> Cart (Circular sync)
        user.setCart(cart);
        cartsByUserId.put(user.getUserId(), cart);
    }

    // --- Public API for Services ---

    public Optional<GroceryStore> findStoreById(String id) {
        return Optional.ofNullable(stores.get(id));
    }

    public Optional<GroceryProduct> findProductById(String productId, String outletId) {
        return products.stream()
                .filter(groceryProduct ->
                        groceryProduct.getProductId().equals(productId)
                                && groceryProduct.getStore().getOutletId().equals(outletId))
                .findFirst();
    }

    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<Cart> findCartByUserId(String userId) {
        return Optional.ofNullable(cartsByUserId.get(userId));
    }

    public List<GroceryStore> findAllStores() {
        return new ArrayList<>(stores.values());
    }
}
