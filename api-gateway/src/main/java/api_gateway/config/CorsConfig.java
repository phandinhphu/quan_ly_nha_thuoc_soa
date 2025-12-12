package api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // Cho phép tất cả origins (hoặc chỉ định cụ thể như "http://localhost:3000")
        corsConfig.addAllowedOriginPattern("*");
        
        // Cho phép các HTTP methods
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.addAllowedMethod("PATCH");
        
        // Cho phép tất cả headers
        corsConfig.addAllowedHeader("*");
        
        // Cho phép gửi credentials (cookies, authorization headers)
        corsConfig.setAllowCredentials(true);
        
        // Thời gian cache preflight request (seconds)
        corsConfig.setMaxAge(3600L);
        
        // Expose ALL headers để client có thể đọc
        corsConfig.addExposedHeader("*");
        corsConfig.addExposedHeader("Content-Type");
        corsConfig.addExposedHeader("Authorization");
        corsConfig.addExposedHeader("X-Requested-With");
        corsConfig.addExposedHeader("Accept");
        corsConfig.addExposedHeader("Origin");
        corsConfig.addExposedHeader("Access-Control-Request-Method");
        corsConfig.addExposedHeader("Access-Control-Request-Headers");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
