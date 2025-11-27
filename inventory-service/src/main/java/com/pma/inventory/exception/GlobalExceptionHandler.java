package com.pma.inventory.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Global Exception Handler for Inventory Service
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", "Đã xảy ra lỗi: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
