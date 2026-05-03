package com.tw.joi.delivery.service;

import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.seedData.InMemoryDataStore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    InMemoryDataStore datarepo;

    public User fetchUserById(String userId) {
        return datarepo.findUserById(userId).orElseThrow(() -> new JoiNotFoundException("User"));
    }

}
