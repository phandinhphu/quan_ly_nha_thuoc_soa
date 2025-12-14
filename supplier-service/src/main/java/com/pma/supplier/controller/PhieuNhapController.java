package com.pma.supplier.controller;

import com.pma.supplier.dto.ApiResponse;
import com.pma.supplier.dto.PhieuNhapRequest;
import com.pma.supplier.dto.PhieuNhapResponse;
import com.pma.supplier.service.PhieuNhapService;
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
@RequestMapping("/api/suppliers/receipts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PhieuNhapController {
	private final PhieuNhapService phieuNhapService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<PhieuNhapResponse>> createPhieuNhap(@Valid @RequestBody PhieuNhapRequest request,
			@RequestHeader(value = "Authorization") String authHeader) {
		log.info("Nhận yêu cầu tạo phiếu nhập: {}", request.getMaPhieuNhap());

		// Extract token từ header
		String token = authHeader.substring(7); // Bỏ "Bearer "

		PhieuNhapResponse phieuNhap = phieuNhapService.createPhieuNhap(request, token);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(phieuNhap, "Tạo phiếu nhập thành công"));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<PhieuNhapResponse>>> getAllPhieuNhap() {
		log.info("Nhận yêu cầu lấy danh sách phiếu nhập");

		List<PhieuNhapResponse> list = phieuNhapService.getAllPhieuNhap();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách phiếu nhập thành công"));
	}

	@GetMapping("/{maPhieuNhap}")
	public ResponseEntity<ApiResponse<PhieuNhapResponse>> getPhieuNhap(@PathVariable String maPhieuNhap) {
		log.info("Nhận yêu cầu lấy thông tin phiếu nhập: {}", maPhieuNhap);

		PhieuNhapResponse phieuNhap = phieuNhapService.getPhieuNhap(maPhieuNhap);

		return ResponseEntity.ok(ApiResponse.success(phieuNhap, "Lấy thông tin phiếu nhập thành công"));
	}

	@DeleteMapping("/{maPhieuNhap}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePhieuNhap(@PathVariable String maPhieuNhap) {
		log.info("Nhận yêu cầu xóa phiếu nhập: {}", maPhieuNhap);

		phieuNhapService.deletePhieuNhap(maPhieuNhap);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa phiếu nhập thành công"));
	}
}
