package com.pma.inventory.controller;

import com.pma.inventory.dto.ApiResponse;
import com.pma.inventory.dto.KhoRequest;
import com.pma.inventory.dto.KhoResponse;
import com.pma.inventory.service.IKhoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Validated
@Slf4j
public class KhoController {
    
    private final IKhoService khoService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<KhoResponse>> createKho(@Valid @RequestBody KhoRequest request) {
        log.info("Nhận yêu cầu tạo kho: {}", request.getMaKho());
        
        KhoResponse kho = khoService.createKho(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(kho, "Tạo kho thành công"));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<KhoResponse>>> getAllKho() {
        log.info("Nhận yêu cầu lấy danh sách kho");
        
        List<KhoResponse> list = khoService.getAllKho();
        
        return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách kho thành công"));
    }
    
    @GetMapping("/{maKho}")
    public ResponseEntity<ApiResponse<KhoResponse>> getKho(@PathVariable String maKho) {
        log.info("Nhận yêu cầu lấy thông tin kho: {}", maKho);
        
        KhoResponse kho = khoService.getKho(maKho);
        
        return ResponseEntity.ok(ApiResponse.success(kho, "Lấy thông tin kho thành công"));
    }
    
    @PutMapping("/{maKho}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<KhoResponse>> updateKho(
            @PathVariable String maKho,
            @Valid @RequestBody KhoRequest request) {
        log.info("Nhận yêu cầu cập nhật kho: {}", maKho);
        
        KhoResponse kho = khoService.updateKho(maKho, request);
        
        return ResponseEntity.ok(ApiResponse.success(kho, "Cập nhật kho thành công"));
    }
    
    @DeleteMapping("/{maKho}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteKho(@PathVariable String maKho) {
        log.info("Nhận yêu cầu xóa kho: {}", maKho);
        
        khoService.deleteKho(maKho);
        
        return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa kho thành công"));
    }
}
