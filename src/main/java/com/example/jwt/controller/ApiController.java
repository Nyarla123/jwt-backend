package com.example.jwt.controller;

import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/login")
    public String login() {

        return "로그인 페이지";
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
    public ResponseEntity<?> admin(@RequestBody User user) {

        log.info("user={}", user);

        return new ResponseEntity<>(userService.findAllUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonProperty("user")
    public ResponseEntity<?> users(@RequestBody User user) {

        log.info("user={}", user);
        System.out.println(user);

        return new ResponseEntity<>(userService.findAllUser(user), HttpStatus.OK);
    }

}
