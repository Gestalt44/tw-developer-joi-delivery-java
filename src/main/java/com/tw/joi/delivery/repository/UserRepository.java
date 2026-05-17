package com.tw.joi.delivery.repository;

import com.tw.joi.delivery.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
