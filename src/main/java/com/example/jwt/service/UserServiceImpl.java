package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exception.DuplicatedUsernameException;
import com.example.jwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    @Transactional
    public void save(User user) {

        userMapper.save(user);
    }


}
