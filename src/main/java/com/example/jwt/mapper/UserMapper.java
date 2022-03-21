package com.example.jwt.mapper;

import com.example.jwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAllUser();
    User findByUserName(String username);
    void save(User user);

}
