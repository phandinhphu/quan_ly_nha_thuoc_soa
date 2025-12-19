package com.pma.sales.controller;

import com.pma.sales.dto.ApiResponse;
import com.pma.sales.dto.HoaDonRequest;
import com.pma.sales.dto.HoaDonResponse;
import com.pma.sales.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sales/invoices")
@RequiredArgsConstructor
@Validated
@Slf4j
public class HoaDonController {
	private final HoaDonService hoaDonService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
	public ResponseEntity<ApiResponse<HoaDonResponse>> createHoaDon(@Valid @RequestBody HoaDonRequest request,
			@RequestHeader(value = "Authorization") String authHeader) {
		log.info("Nhận yêu cầu tạo hóa đơn: {}", request.getMaHoaDon());

		// Extract token từ header
		String token = authHeader.substring(7); // Bỏ "Bearer "

		HoaDonResponse hoaDon = hoaDonService.createHoaDon(request, token);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(hoaDon, "Tạo hóa đơn thành công"));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<HoaDonResponse>>> getAllHoaDon() {
		log.info("Nhận yêu cầu lấy danh sách hóa đơn");

		List<HoaDonResponse> list = hoaDonService.getAllHoaDon();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách hóa đơn thành công"));
	}

	@GetMapping("/{maHoaDon}")
	public ResponseEntity<ApiResponse<HoaDonResponse>> getHoaDon(@PathVariable String maHoaDon) {
		log.info("Nhận yêu cầu lấy thông tin hóa đơn: {}", maHoaDon);

		HoaDonResponse hoaDon = hoaDonService.getHoaDon(maHoaDon);

		return ResponseEntity.ok(ApiResponse.success(hoaDon, "Lấy thông tin hóa đơn thành công"));
	}

	@DeleteMapping("/{maHoaDon}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteHoaDon(@PathVariable String maHoaDon) {
		log.info("Nhận yêu cầu xóa hóa đơn: {}", maHoaDon);

		hoaDonService.deleteHoaDon(maHoaDon);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa hóa đơn thành công"));
	}
}

