package com.pma.sales.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            .requestMatchers("/api/auth/**").permitAll()
	            .requestMatchers("/actuator/**").permitAll()
	            .requestMatchers("/h2-console/**").permitAll()
	            .requestMatchers(
	                    "/v3/api-docs/**",
	                    "/swagger-ui/**",
	                    "/swagger-ui.html"
	            ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sales/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/sales/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/sales/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/sales/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Cho phép H2 Console

        return http.build();
    }
}

