package com.example.jwt.mapper;

import com.example.jwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    List<User> findAllUser(User user);
    User findByUserName(String username);
    void save(User user);

}
