package com.tw.joi.delivery.repository;

import com.tw.joi.delivery.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
