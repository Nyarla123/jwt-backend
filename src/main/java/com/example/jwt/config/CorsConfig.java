package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할떄 json을 자바스크립에서 처리할 수 있게 설정
        config.addAllowedOriginPattern("*"); // 모든 ip에 응답 허용
        config.addAllowedHeader("*"); // 모든 header에 응답 허용
        config.addAllowedMethod("*"); // 모든 post,get,delete,put,fetch 요청 허용

        // token 받기 위한 header exposed 설정
        List<String> exposedHeaders = new ArrayList<>();// 노출할 헤더명을 담을 리스트
        exposedHeaders.add("Authorization");
        config.setExposedHeaders(exposedHeaders); // 노출헤더 세팅

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
