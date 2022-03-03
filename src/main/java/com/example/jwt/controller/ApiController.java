package com.example.jwt.controller;

import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String home() {

        return "안녕하세요 현재 서버시간은 " + new Date() +"입니다\n";
    }

    @PostMapping("/token")
    public String token() {

        return "token";
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userService.save(user);

        return "회원가입완료";
    }

    // user와 admin접근가능
    @GetMapping("/user")
    public String user(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails);
        return "user";
    }

    // admin접근가능
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
