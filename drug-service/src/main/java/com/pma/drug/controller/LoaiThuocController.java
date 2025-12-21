package com.pma.drug.controller;

import com.pma.drug.dto.ApiResponse;
import com.pma.drug.dto.LoaiThuocRequest;
import com.pma.drug.dto.paginate.PageResponse;
import com.pma.drug.entity.LoaiThuoc;
import com.pma.drug.mapper.PageResponseMapper;
import com.pma.drug.service.LoaiThuocService;
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
@RequestMapping("/api/drugs/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LoaiThuocController {

	private final LoaiThuocService loaiThuocService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<Map<String, Object>> createLoaiThuoc(@Valid @RequestBody LoaiThuocRequest request) {
		log.info("Nhận yêu cầu tạo loại thuốc: {}", request.getMaLoai());

		LoaiThuoc loaiThuoc = loaiThuocService.createLoaiThuoc(request);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Tạo loại thuốc thành công");
		response.put("data", loaiThuoc);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllLoaiThuoc() {
		log.info("Nhận yêu cầu lấy danh sách loại thuốc");

		List<LoaiThuoc> list = loaiThuocService.getAllLoaiThuoc();

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Lấy danh sách loại thuốc thành công");
		response.put("data", list);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<LoaiThuoc>>> searchLoaiThuoc(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String tenLoai) {
		log.info("Nhận yêu cầu tìm kiếm loại thuốc - trang: {}, kích thước: {}", page, size);

		Page<LoaiThuoc> loaiThuocPage = loaiThuocService.searchLoaiThuocByName(tenLoai, page, size);

		return ResponseEntity
				.ok(ApiResponse.success(PageResponseMapper.from(loaiThuocPage), "Lấy danh sách loại thuốc thành công"));
	}

	@GetMapping("/{maLoai}")
	public ResponseEntity<Map<String, Object>> getLoaiThuoc(@PathVariable String maLoai) {
		log.info("Nhận yêu cầu lấy thông tin loại thuốc: {}", maLoai);

		LoaiThuoc loaiThuoc = loaiThuocService.getLoaiThuoc(maLoai);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Lấy thông tin loại thuốc thành công");
		response.put("data", loaiThuoc);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{maLoai}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<Map<String, Object>> updateLoaiThuoc(@PathVariable String maLoai,
			@Valid @RequestBody LoaiThuocRequest request) {
		log.info("Nhận yêu cầu cập nhật loại thuốc: {}", maLoai);

		LoaiThuoc loaiThuoc = loaiThuocService.updateLoaiThuoc(maLoai, request);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Cập nhật loại thuốc thành công");
		response.put("data", loaiThuoc);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{maLoai}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> deleteLoaiThuoc(@PathVariable String maLoai) {
		log.info("Nhận yêu cầu xóa loại thuốc: {}", maLoai);

		loaiThuocService.deleteLoaiThuoc(maLoai);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Xóa loại thuốc thành công");

		return ResponseEntity.ok(response);
	}
}
