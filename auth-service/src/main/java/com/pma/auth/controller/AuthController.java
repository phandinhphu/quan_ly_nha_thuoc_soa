package com.pma.auth.controller;

import com.pma.auth.dto.LoginRequest;
import com.pma.auth.dto.RegisterRequest;
import com.pma.auth.dto.UserDetailsResponse;
import com.pma.auth.dto.VerifyTokenRequest;
import com.pma.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * API đăng ký tài khoản mới
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Nhận yêu cầu đăng ký từ: {}", request.getTenDangNhap());
        
        String token = authService.register(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đăng ký tài khoản thành công");
        response.put("data", Map.of("token", token));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * API đăng nhập
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Nhận yêu cầu đăng nhập từ: {}", request.getTenDangNhap());
        
        String token = authService.login(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đăng nhập thành công");
        response.put("data", Map.of("token", token));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * API xác thực token (cho các service khác)
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@Valid @RequestBody VerifyTokenRequest request) {
        log.info("Nhận yêu cầu xác thực token");
        
        UserDetailsResponse userDetails = authService.verifyToken(request.getToken());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Token hợp lệ");
        response.put("data", userDetails);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * API xác thực token (alternative - dùng header)
     */
    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyTokenHeader(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("Nhận yêu cầu xác thực token từ header");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Token không hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        
        String token = authHeader.substring(7);
        UserDetailsResponse userDetails = authService.verifyToken(token);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Token hợp lệ");
        response.put("data", userDetails);
        
        return ResponseEntity.ok(response);
    }
}
