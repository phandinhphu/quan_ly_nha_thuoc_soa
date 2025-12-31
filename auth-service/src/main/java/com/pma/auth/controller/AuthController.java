package com.pma.auth.controller;

import com.pma.auth.dto.ApiResponse;
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
	public ResponseEntity<ApiResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterRequest request) {
		log.info("Nhận yêu cầu đăng ký từ: {}", request.getTenDangNhap());

		String token = authService.register(request);

		return ResponseEntity.ok(ApiResponse.success(Map.of("token", token), "Đăng ký thành công"));
	}

	/**
	 * API đăng nhập
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest request) {
		log.info("Nhận yêu cầu đăng nhập từ: {}", request.getTenDangNhap());

		String token = authService.login(request);

		return ResponseEntity.ok(ApiResponse.success(Map.of("token", token), "Đăng ký thành công"));
	}

	/**
	 * API xác thực token (cho các service khác)
	 */
	@PostMapping("/verify")
	public ResponseEntity<ApiResponse<UserDetailsResponse>> verifyToken(
			@Valid @RequestBody VerifyTokenRequest request) {
		log.info("Nhận yêu cầu xác thực token");

		UserDetailsResponse userDetails = authService.verifyToken(request.getToken());

		return ResponseEntity.ok(ApiResponse.success(userDetails, "Token hợp lệ"));
	}

	/**
	 * API xác thực token (alternative - dùng header)
	 */
	@GetMapping("/verify")
	public ResponseEntity<ApiResponse<UserDetailsResponse>> verifyTokenHeader(
			@RequestHeader(value = "Authorization", required = false) String authHeader) {
		log.info("Nhận yêu cầu xác thực token từ header");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body(ApiResponse.failure("Token không hợp lệ"));
		}

		String token = authHeader.substring(7);
		UserDetailsResponse userDetails = authService.verifyToken(token);

		return ResponseEntity.ok(ApiResponse.success(userDetails, "Token hợp lệ"));
	}

	/**
	 * API lấy thông tin người dùng từ token (/get/me)
	 */
	@GetMapping("/get/me")
	public ResponseEntity<ApiResponse<UserDetailsResponse>> getMe(
			@RequestHeader(value = "Authorization", required = false) String authHeader) {
		log.info("Nhận yêu cầu lấy thông tin người dùng từ token");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body(ApiResponse.failure("Token không hợp lệ"));
		}

		String token = authHeader.substring(7);
		UserDetailsResponse userDetails = authService.verifyToken(token);

		return ResponseEntity.ok(ApiResponse.success(userDetails, "Lấy thông tin người dùng thành công"));
	}
}