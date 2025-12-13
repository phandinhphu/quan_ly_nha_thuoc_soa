package com.pma.drug.controller;

import com.pma.drug.dto.ThuocRequest;
import com.pma.drug.dto.ThuocResponse;
import com.pma.drug.dto.UpdateStockRequest;
import com.pma.drug.dto.paginate.ApiResponse;
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
    public ResponseEntity<Map<String, Object>> createThuoc(@Valid @RequestBody ThuocRequest request) {
    	System.out.println("Received request to create drug: " + request.getMaThuoc());
        log.info("Nhận yêu cầu tạo thuốc: {}", request.getMaThuoc());
        
        ThuocResponse thuoc = thuocService.createThuoc(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo thuốc thành công");
        response.put("data", thuoc);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllThuoc() {
        log.info("Nhận yêu cầu lấy danh sách thuốc");
        
        List<ThuocResponse> list = thuocService.getAllThuoc();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách thuốc thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{maThuoc}")
    public ResponseEntity<Map<String, Object>> getThuoc(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu lấy thông tin thuốc: {}", maThuoc);
        
        ThuocResponse thuoc = thuocService.getThuoc(maThuoc);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin thuốc thành công");
        response.put("data", thuoc);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ApiResponse<PageResponse<ThuocResponse>> searchThuoc(
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String tenThuoc
    		) {
        log.info("Nhận yêu cầu tìm kiếm thuốc theo tên: {}", tenThuoc);
        
        Page<Thuoc> resultPage = thuocService.searchThuocByName(tenThuoc, page, size);
        
        Page<ThuocResponse> pageResponse = resultPage.map(ThuocMapper::toResponse);
        
        return ApiResponse.success(PageResponseMapper.from(pageResponse));
    }
    
    @GetMapping("/category/{maLoai}")
    public ResponseEntity<Map<String, Object>> getThuocByCategory(@PathVariable String maLoai) {
        log.info("Nhận yêu cầu lấy thuốc theo loại: {}", maLoai);
        
        List<ThuocResponse> list = thuocService.getThuocByLoai(maLoai);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách thuốc theo loại thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getLowStockDrugs(@RequestParam(defaultValue = "10") Integer threshold) {
        log.info("Nhận yêu cầu lấy thuốc sắp hết hàng, ngưỡng: {}", threshold);
        
        List<ThuocResponse> list = thuocService.getLowStockDrugs(threshold);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách thuốc sắp hết hàng thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/expiring")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getExpiringDrugs(@RequestParam(defaultValue = "30") Integer daysAhead) {
        log.info("Nhận yêu cầu lấy thuốc sắp hết hạn, số ngày: {}", daysAhead);
        
        List<ThuocResponse> list = thuocService.getExpiringDrugs(daysAhead);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách thuốc sắp hết hạn thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{maThuoc}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateThuoc(
            @PathVariable String maThuoc,
            @Valid @RequestBody ThuocRequest request) {
        log.info("Nhận yêu cầu cập nhật thuốc: {}", maThuoc);
        
        ThuocResponse thuoc = thuocService.updateThuoc(maThuoc, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật thuốc thành công");
        response.put("data", thuoc);
        
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{maThuoc}/stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Map<String, Object>> updateStock(
            @PathVariable String maThuoc,
            @Valid @RequestBody UpdateStockRequest request) {
        log.info("Nhận yêu cầu cập nhật tồn kho cho thuốc: {}", maThuoc);
        
        ThuocResponse thuoc = thuocService.updateStock(maThuoc, request.getSoLuong());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật tồn kho thành công");
        response.put("data", thuoc);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{maThuoc}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteThuoc(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu xóa thuốc: {}", maThuoc);
        
        thuocService.deleteThuoc(maThuoc);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa thuốc thành công");
        
        return ResponseEntity.ok(response);
    }
}
