package com.pma.supplier.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "inventory-service")
public interface IInventoryServiceClient {
	@PostMapping("/api/inventory-history")
	ResponseEntity<Map<String, Object>> createInventoryHistory(@RequestBody Map<String, Object> body,
			@RequestHeader("Authorization") String authorization);
}
