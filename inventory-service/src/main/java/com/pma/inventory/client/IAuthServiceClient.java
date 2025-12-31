package com.pma.inventory.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface IAuthServiceClient {

	@GetMapping("/api/auth/verify")
	ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authorization);
	
}
