package com.pma.drug.controller;

import com.pma.drug.dto.ApiResponse;
import com.pma.drug.dto.ThuocRequest;
import com.pma.drug.dto.ThuocResponse;
import com.pma.drug.dto.UpdateStockRequest;
import com.pma.drug.dto.paginate.PageResponse;
import com.pma.drug.entity.*;
import com.pma.drug.mapper.PageResponseMapper;
import com.pma.drug.mapper.ThuocMapper;
import com.pma.drug.service.ThuocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
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
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ThuocController {

	private final ThuocService thuocService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<ThuocResponse>> createThuoc(@Valid @RequestBody ThuocRequest request) {
		System.out.println("Received request to create drug: " + request.getMaThuoc());
		log.info("Nhận yêu cầu tạo thuốc: {}", request.getMaThuoc());

		ThuocResponse thuoc = thuocService.createThuoc(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(thuoc, "Tạo thuốc thành công"));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ThuocResponse>>> getAllThuoc() {
		log.info("Nhận yêu cầu lấy danh sách thuốc");

		List<ThuocResponse> list = thuocService.getAllThuoc();

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách thuốc thành công"));
	}

	@GetMapping("/{maThuoc}")
	public ResponseEntity<ApiResponse<ThuocResponse>> getThuoc(@PathVariable String maThuoc) {
		log.info("Nhận yêu cầu lấy thông tin thuốc: {}", maThuoc);

		ThuocResponse thuoc = thuocService.getThuoc(maThuoc);

		return ResponseEntity.ok(ApiResponse.success(thuoc, "Lấy thông tin thuốc thành công"));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<ThuocResponse>>> searchThuoc(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String tenThuoc) {
		log.info("Nhận yêu cầu tìm kiếm thuốc theo tên: {}", tenThuoc);

		Page<Thuoc> resultPage = thuocService.searchThuocByName(tenThuoc, page, size);

		Page<ThuocResponse> pageResponse = resultPage.map(ThuocMapper::toResponse);

		return ResponseEntity
				.ok(ApiResponse.success(PageResponseMapper.from(pageResponse), "Lấy danh sách thuốc thành công"));
	}

	@GetMapping("/category/{maLoai}")
	public ResponseEntity<ApiResponse<List<ThuocResponse>>> getThuocByCategory(@PathVariable String maLoai) {
		log.info("Nhận yêu cầu lấy thuốc theo loại: {}", maLoai);

		List<ThuocResponse> list = thuocService.getThuocByLoai(maLoai);

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách thuốc theo loại thành công"));
	}

	@GetMapping("/low-stock")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<List<ThuocResponse>>> getLowStockDrugs(
			@RequestParam(defaultValue = "10") Integer threshold) {
		log.info("Nhận yêu cầu lấy thuốc sắp hết hàng, ngưỡng: {}", threshold);

		List<ThuocResponse> list = thuocService.getLowStockDrugs(threshold);

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách thuốc sắp hết hàng thành công"));
	}

	@GetMapping("/expiring")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<List<ThuocResponse>>> getExpiringDrugs(
			@RequestParam(defaultValue = "30") Integer daysAhead) {
		log.info("Nhận yêu cầu lấy thuốc sắp hết hạn, số ngày: {}", daysAhead);

		List<ThuocResponse> list = thuocService.getExpiringDrugs(daysAhead);

		return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách thuốc sắp hết hạn thành công"));
	}

	@PutMapping("/{maThuoc}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<ApiResponse<ThuocResponse>> updateThuoc(@PathVariable String maThuoc,
			@Valid @RequestBody ThuocRequest request) {
		log.info("Nhận yêu cầu cập nhật thuốc: {}", maThuoc);

		ThuocResponse thuoc = thuocService.updateThuoc(maThuoc, request);

		return ResponseEntity.ok(ApiResponse.success(thuoc, "Cập nhật thuốc thành công"));
	}

	@PatchMapping("/{maThuoc}/stock")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER', 'STAFF')")
	public ResponseEntity<ApiResponse<ThuocResponse>> updateStock(@PathVariable String maThuoc,
			@Valid @RequestBody UpdateStockRequest request) {
		log.info("Nhận yêu cầu cập nhật tồn kho cho thuốc: {}", maThuoc);

		ThuocResponse thuoc = thuocService.updateStock(maThuoc, request.getSoLuong());

		return ResponseEntity.ok(ApiResponse.success(thuoc, "Cập nhật tồn kho thành công"));
	}

	@DeleteMapping("/{maThuoc}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteThuoc(@PathVariable String maThuoc) {
		log.info("Nhận yêu cầu xóa thuốc: {}", maThuoc);

		thuocService.deleteThuoc(maThuoc);

		return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa thuốc thành công"));
	}
}
