package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String home() {

        return "home";
    }

    @PostMapping("/token")
    public String token() {

        return "token";
    }

    @PostMapping("join")
    public String join(@RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userService.save(user);

        return "회원가입완료";
    }

}
