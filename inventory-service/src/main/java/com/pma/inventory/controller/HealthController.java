package com.pma.inventory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public ResponseEntity<Map<String, Object>> healthCheck() {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "UP");
		response.put("message", "Inventory Service is running");
		response.put("timestamp", System.currentTimeMillis());
		
		return ResponseEntity.ok(response);
	}

}
