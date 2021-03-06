package com.example.jwt.controller;

import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.entity.LoginDto;
import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/save")
    public String save(@RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
    public ResponseEntity<List<User>> admin() {

        return ResponseEntity.ok(userService.findAllUser());
    }

}
