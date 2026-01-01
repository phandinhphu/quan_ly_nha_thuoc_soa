package com.pma.inventory.controller;

import com.pma.inventory.dto.ApiResponse;
import com.pma.inventory.dto.LichSuTonRequest;
import com.pma.inventory.dto.LichSuTonResponse;
import com.pma.inventory.service.ILichSuTonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inventory-history")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LichSuTonController {

	private final ILichSuTonService lichSuTonService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<LichSuTonResponse>> createLichSuTon(
			@Valid @RequestBody LichSuTonRequest request) {
		log.info("Nhận yêu cầu tạo lịch sử tồn cho thuốc: {}", request.getMaThuoc());

		LichSuTonResponse lichSu = lichSuTonService.createLichSuTon(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(lichSu, "Tạo lịch sử tồn thành công"));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<LichSuTonResponse>>> getAllLichSuTon() {
		log.info("Nhận yêu cầu lấy danh sách lịch sử tồn");

		List<LichSuTonResponse> list = lichSuTonService.getAllLichSuTon();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách lịch sử tồn thành công"));
	}

	@GetMapping("/{maLS}")
	public ResponseEntity<ApiResponse<LichSuTonResponse>> getLichSuTon(@PathVariable Integer maLS) {
		log.info("Nhận yêu cầu lấy thông tin lịch sử tồn: {}", maLS);

		LichSuTonResponse lichSu = lichSuTonService.getLichSuTon(maLS);

		return ResponseEntity.ok(ApiResponse.success(lichSu, "Lấy thông tin lịch sử tồn thành công"));
	}

	@GetMapping("/drug/{maThuoc}")
	public ResponseEntity<ApiResponse<List<LichSuTonResponse>>> getLichSuByMaThuoc(@PathVariable String maThuoc) {
		log.info("Nhận yêu cầu lấy lịch sử tồn theo mã thuốc: {}", maThuoc);

		List<LichSuTonResponse> list = lichSuTonService.getLichSuByMaThuoc(maThuoc);

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy lịch sử tồn theo mã thuốc thành công"));
	}

	@GetMapping("/date-range")
	public ResponseEntity<ApiResponse<List<LichSuTonResponse>>> getLichSuByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		log.info("Nhận yêu cầu lấy lịch sử tồn từ {} đến {}", start, end);

		List<LichSuTonResponse> list = lichSuTonService.getLichSuByDateRange(start, end);

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy lịch sử tồn theo khoảng thời gian thành công"));
	}

	@GetMapping("/recent")
	public ResponseEntity<ApiResponse<List<LichSuTonResponse>>> getRecentLichSuTon() {
		log.info("Nhận yêu cầu lấy 10 lịch sử tồn gần nhất");

		List<LichSuTonResponse> list = lichSuTonService.getRecentLichSuTon();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy 10 lịch sử tồn gần nhất thành công"));
	}

	@DeleteMapping("/{maLS}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> deleteLichSuTon(@PathVariable Integer maLS) {
		log.info("Nhận yêu cầu xóa lịch sử tồn: {}", maLS);

		lichSuTonService.deleteLichSuTon(maLS);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa lịch sử tồn thành công"));
	}
}
