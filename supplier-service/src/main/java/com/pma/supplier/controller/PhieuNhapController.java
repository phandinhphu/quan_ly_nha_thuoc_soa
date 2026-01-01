package com.pma.supplier.controller;

import com.pma.supplier.dto.ApiResponse;
import com.pma.supplier.dto.PhieuNhapRequest;
import com.pma.supplier.dto.PhieuNhapResponse;
import com.pma.supplier.dto.paginate.PageResponse;
import com.pma.supplier.mapper.PageResponseMapper;
import com.pma.supplier.service.IPhieuNhapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers/receipts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PhieuNhapController {
	private final IPhieuNhapService phieuNhapService;

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

	@GetMapping("/page")
	public ResponseEntity<ApiResponse<PageResponse<PhieuNhapResponse>>> getPhieuNhapPaginated(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		log.info("Nhận yêu cầu lấy danh sách phiếu nhập phân trang: trang {}, kích thước {}", page, size);

		Page<PhieuNhapResponse> response = phieuNhapService.getPhieuNhapByPage(page, size);

		return ResponseEntity.ok(ApiResponse.success(PageResponseMapper.from(response),
				"Lấy danh sách phiếu nhập phân trang thành công"));
	}

	@GetMapping("/{maPhieuNhap}")
	public ResponseEntity<ApiResponse<PhieuNhapResponse>> getPhieuNhap(@PathVariable String maPhieuNhap) {
		log.info("Nhận yêu cầu lấy thông tin phiếu nhập: {}", maPhieuNhap);

		PhieuNhapResponse phieuNhap = phieuNhapService.getPhieuNhap(maPhieuNhap);

		return ResponseEntity.ok(ApiResponse.success(phieuNhap, "Lấy thông tin phiếu nhập thành công"));
	}

	@DeleteMapping("/{maPhieuNhap}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> deletePhieuNhap(@PathVariable String maPhieuNhap) {
		log.info("Nhận yêu cầu xóa phiếu nhập: {}", maPhieuNhap);

		phieuNhapService.deletePhieuNhap(maPhieuNhap);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa phiếu nhập thành công"));
	}
}
