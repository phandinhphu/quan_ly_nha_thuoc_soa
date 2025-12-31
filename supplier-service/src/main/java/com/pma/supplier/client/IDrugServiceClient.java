package com.pma.supplier.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "drug-service")
public interface IDrugServiceClient {

	@GetMapping("/api/drugs/{maThuoc}")
	ResponseEntity<Map<String, Object>> getDrugByMaThuoc(@PathVariable String maThuoc,
			@RequestHeader("Authorization") String authorization);
	
}
