package com.pma.auth.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pma.auth.dto.ApiResponse;

/**
 * Global Exception Handler for Drug Service
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(ex.getMessage()));
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleValidationException(ValidationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(ex.getMessage()));
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.failure(ex.getMessage()));
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ApiResponse.failure("Bạn không có quyền truy cập tài nguyên này"));
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ApiResponse.failure("Xác thực không thành công: " + ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		response.put("success", false);
		response.put("message", "Validation failed");
		response.put("errors", errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.failure("Đã xảy ra lỗi nội bộ máy chủ! " + ex.getMessage()));
	}
}
