package com.pma.supplier.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
                .requestMatchers(HttpMethod.GET, "/api/suppliers/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/suppliers/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/suppliers/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/suppliers/**").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(
                        "{\"success\":false,\"message\":\"Xác thực không thành công\",\"data\":null}"
                    );
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(
                        "{\"success\":false,\"message\":\"Bạn không có quyền truy cập tài nguyên này\",\"data\":null}"
                    );
                })
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Cho phép H2 Console

        return http.build();    
    }
}
