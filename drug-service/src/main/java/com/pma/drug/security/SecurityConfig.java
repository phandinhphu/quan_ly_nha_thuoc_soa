package com.pma.drug.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable()) // hoặc cấu hình CorsConfigurationSource riêng
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/drugs/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/drugs/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/drugs/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/drugs/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Cho phép H2 Console

        return http.build();
    }

}
