package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> {
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users", "/users/login").permitAll()
                        .requestMatchers("/login").authenticated()
                        .requestMatchers("/suspicious/**", "/session/active")
                        .hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers("/session/**")
                        .hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers("/users/**", "/RBAC/create")
                        .hasRole("SUPERADMIN")
                        .anyRequest().authenticated());

        return http.build();
    }
}