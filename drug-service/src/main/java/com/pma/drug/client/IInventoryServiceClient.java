package com.pma.drug.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.pma.drug.dto.CanhBaoTonRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "inventory-service")
public interface IInventoryServiceClient {
	@PostMapping("/api/stock-alerts")
	ResponseEntity<Map<String, Object>> createCanhBaoTon(@RequestBody CanhBaoTonRequest request,
			@RequestHeader("Authorization") String authorization);

	@PutMapping("/api/stock-alerts/{maThuoc}")
	ResponseEntity<Map<String, Object>> updateCanhBaoTon(@PathVariable String maThuoc,
			@RequestBody CanhBaoTonRequest request, @RequestHeader("Authorization") String authorization);
	
	@GetMapping("/api/stock-alerts/{maThuoc}")
	ResponseEntity<Map<String, Object>> getCanhBaoTon(@PathVariable String maThuoc,
			@RequestHeader("Authorization") String authorization);
	
	@PutMapping("/api/stock-alerts/{maThuoc}/current-stock")
	ResponseEntity<Map<String, Object>> updateCurrentStock(@PathVariable String maThuoc,
			@RequestParam Integer soLuongHienTai,
			@RequestHeader("Authorization") String authorization);
}
