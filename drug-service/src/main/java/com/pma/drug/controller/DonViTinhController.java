package com.pma.drug.controller;

import com.pma.drug.dto.ApiResponse;
import com.pma.drug.dto.DonViTinhRequest;
import com.pma.drug.dto.paginate.PageResponse;
import com.pma.drug.entity.DonViTinh;
import com.pma.drug.mapper.PageResponseMapper;
import com.pma.drug.service.DonViTinhService;
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
@RequestMapping("/api/drugs/units")
@RequiredArgsConstructor
@Validated
@Slf4j
public class DonViTinhController {
    
    private final DonViTinhService donViTinhService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> createDonViTinh(@Valid @RequestBody DonViTinhRequest request) {
        log.info("Nhận yêu cầu tạo đơn vị tính: {}", request.getMaDonVi());
        
        DonViTinh donViTinh = donViTinhService.createDonViTinh(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo đơn vị tính thành công");
        response.put("data", donViTinh);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDonViTinh() {
        log.info("Nhận yêu cầu lấy danh sách đơn vị tính");
        
        List<DonViTinh> list = donViTinhService.getAllDonViTinh();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách đơn vị tính thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<DonViTinh>>> searchDonViTinh(
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("Nhận yêu cầu tìm kiếm đơn vị tính với từ khóa: {}", keyword);
		
		Page<DonViTinh> pageResponse = donViTinhService.searchDonViTinh(keyword, page, size);
		
		return ResponseEntity.ok(ApiResponse.success(PageResponseMapper.from(pageResponse), "Lấy danh sách đơn vị tính thành công"));
	}
    
    @GetMapping("/{maDonVi}")
    public ResponseEntity<Map<String, Object>> getDonViTinh(@PathVariable String maDonVi) {
        log.info("Nhận yêu cầu lấy thông tin đơn vị tính: {}", maDonVi);
        
        DonViTinh donViTinh = donViTinhService.getDonViTinh(maDonVi);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin đơn vị tính thành công");
        response.put("data", donViTinh);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{maDonVi}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateDonViTinh(
            @PathVariable String maDonVi,
            @Valid @RequestBody DonViTinhRequest request) {
        log.info("Nhận yêu cầu cập nhật đơn vị tính: {}", maDonVi);
        
        DonViTinh donViTinh = donViTinhService.updateDonViTinh(maDonVi, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật đơn vị tính thành công");
        response.put("data", donViTinh);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{maDonVi}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteDonViTinh(@PathVariable String maDonVi) {
        log.info("Nhận yêu cầu xóa đơn vị tính: {}", maDonVi);
        
        donViTinhService.deleteDonViTinh(maDonVi);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa đơn vị tính thành công");
        
        return ResponseEntity.ok(response);
    }
}
