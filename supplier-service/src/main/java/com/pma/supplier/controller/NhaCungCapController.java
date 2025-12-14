package com.pma.supplier.controller;

import com.pma.supplier.dto.ApiResponse;
import com.pma.supplier.dto.NhaCungCapRequest;
import com.pma.supplier.entity.NhaCungCap;
import com.pma.supplier.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Validated
@Slf4j
public class NhaCungCapController {
	private final NhaCungCapService nhaCungCapService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<NhaCungCap>> createNhaCungCap(@Valid @RequestBody NhaCungCapRequest request) {
		log.info("Nhận yêu cầu tạo nhà cung cấp: {}", request.getMaNCC());

		NhaCungCap ncc = nhaCungCapService.createNhaCungCap(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(ncc, "Tạo nhà cung cấp thành công"));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<NhaCungCap>>> getAllNhaCungCap() {
		log.info("Nhận yêu cầu lấy danh sách nhà cung cấp");

		List<NhaCungCap> list = nhaCungCapService.getAllNhaCungCap();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách nhà cung cấp thành công"));
	}

	@GetMapping("/{maNCC}")
	public ResponseEntity<ApiResponse<NhaCungCap>> getNhaCungCap(@PathVariable String maNCC) {
		log.info("Nhận yêu cầu lấy thông tin nhà cung cấp: {}", maNCC);

		NhaCungCap ncc = nhaCungCapService.getNhaCungCap(maNCC);

		return ResponseEntity.ok(ApiResponse.success(ncc, "Lấy thông tin nhà cung cấp thành công"));
	}

	@PutMapping("/{maNCC}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<NhaCungCap>> updateNhaCungCap(@PathVariable String maNCC,
			@Valid @RequestBody NhaCungCapRequest request) {
		log.info("Nhận yêu cầu cập nhật nhà cung cấp: {}", maNCC);

		NhaCungCap ncc = nhaCungCapService.updateNhaCungCap(maNCC, request);

		return ResponseEntity.ok(ApiResponse.success(ncc, "Cập nhật nhà cung cấp thành công"));
	}

	@DeleteMapping("/{maNCC}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteNhaCungCap(@PathVariable String maNCC) {
		log.info("Nhận yêu cầu xóa nhà cung cấp: {}", maNCC);

		nhaCungCapService.deleteNhaCungCap(maNCC);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa nhà cung cấp thành công"));
	}
}
