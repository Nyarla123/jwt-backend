package com.example.jwt.service;

import com.example.jwt.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllUser(User user);
    User findByUserName(String username);
    void save(User user);

}
