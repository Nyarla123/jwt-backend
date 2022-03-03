package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public List<User> findAllUser(User user) {
        return userMapper.findAllUser(user);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
}
