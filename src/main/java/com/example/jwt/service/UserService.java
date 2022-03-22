package com.example.jwt.service;

import com.example.jwt.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUser();
    User findByUserName(String username);
    void save(User user);

}
