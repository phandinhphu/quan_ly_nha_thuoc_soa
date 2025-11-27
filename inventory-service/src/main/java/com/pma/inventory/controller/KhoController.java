package com.pma.inventory.controller;

import com.pma.inventory.dto.KhoRequest;
import com.pma.inventory.dto.KhoResponse;
import com.pma.inventory.service.KhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Validated
@Slf4j
public class KhoController {
    
    private final KhoService khoService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> createKho(@Valid @RequestBody KhoRequest request) {
        log.info("Nhận yêu cầu tạo kho: {}", request.getMaKho());
        
        KhoResponse kho = khoService.createKho(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo kho thành công");
        response.put("data", kho);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllKho() {
        log.info("Nhận yêu cầu lấy danh sách kho");
        
        List<KhoResponse> list = khoService.getAllKho();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách kho thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{maKho}")
    public ResponseEntity<Map<String, Object>> getKho(@PathVariable String maKho) {
        log.info("Nhận yêu cầu lấy thông tin kho: {}", maKho);
        
        KhoResponse kho = khoService.getKho(maKho);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin kho thành công");
        response.put("data", kho);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{maKho}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateKho(
            @PathVariable String maKho,
            @Valid @RequestBody KhoRequest request) {
        log.info("Nhận yêu cầu cập nhật kho: {}", maKho);
        
        KhoResponse kho = khoService.updateKho(maKho, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật kho thành công");
        response.put("data", kho);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{maKho}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteKho(@PathVariable String maKho) {
        log.info("Nhận yêu cầu xóa kho: {}", maKho);
        
        khoService.deleteKho(maKho);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa kho thành công");
        
        return ResponseEntity.ok(response);
    }
}
