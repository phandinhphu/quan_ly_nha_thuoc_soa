package com.pma.drug.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pma.drug.dto.LoginRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "auth-service")
public interface IAuthServiceClient {
	@PostMapping("/api/auth/login")
	ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request);
	@GetMapping("/api/auth/verify")
	ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authorization);
}
