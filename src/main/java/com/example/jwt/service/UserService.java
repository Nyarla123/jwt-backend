package com.example.jwt.service;

import com.example.jwt.entity.User;

public interface UserService {

    User findByUserName(String username);
    void save(User user);

}
