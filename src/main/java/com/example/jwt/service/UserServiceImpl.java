package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User save(User user) {
        return userMapper.save(user);
    }
}
