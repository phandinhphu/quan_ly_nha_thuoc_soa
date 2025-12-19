package com.pma.sales.security;

import com.pma.sales.client.AuthServiceClient;
import com.pma.sales.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // Gọi auth-service để verify token
                UserDetailsDto userDetails = authServiceClient.verifyToken(token);

                if (userDetails != null) {
                    // Tạo authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userDetails.getVaiTro()))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Đã xác thực user: {}", userDetails.getTenDangNhap());
                }
            } catch (Exception e) {
                log.error("Lỗi khi xác thực token: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}

