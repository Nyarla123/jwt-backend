package com.example.jwt.config;

import com.example.jwt.config.jwt.JwtAuthenticationFilter;
import com.example.jwt.config.jwt.JwtAuthorizationFilter;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http
            .csrf().disable()
                // 세션 사용안함
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors().and()
            .addFilter(corsFilter) // CrossOrigin(인증x), 시큐리티 필터에 등록 인증(O)
            .formLogin().disable() // form 로그인 사용안함
            .httpBasic().disable() // http 로그인 방식 사용안함
            .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManger
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService))
            .authorizeRequests()
            .antMatchers("/api/user/**")
            .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/api/admin/**")
            .access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll();
    }
    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers("/api/hello");
    }


}
