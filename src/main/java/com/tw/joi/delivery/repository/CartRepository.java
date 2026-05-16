package com.tw.joi.delivery.repository;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.CartStatus;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {

  Optional<Cart> findByUserUserIdAndOutletOutletIdAndStatus(
      Long userId,
      Long outletId,
      CartStatus status
  );
}
