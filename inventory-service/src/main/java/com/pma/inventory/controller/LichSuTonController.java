package com.pma.inventory.controller;

import com.pma.inventory.dto.LichSuTonRequest;
import com.pma.inventory.dto.LichSuTonResponse;
import com.pma.inventory.service.LichSuTonService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory-history")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LichSuTonController {
    
    private final LichSuTonService lichSuTonService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Map<String, Object>> createLichSuTon(@Valid @RequestBody LichSuTonRequest request) {
        log.info("Nhận yêu cầu tạo lịch sử tồn cho thuốc: {}", request.getMaThuoc());
        
        LichSuTonResponse lichSu = lichSuTonService.createLichSuTon(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo lịch sử tồn thành công");
        response.put("data", lichSu);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllLichSuTon() {
        log.info("Nhận yêu cầu lấy danh sách lịch sử tồn");
        
        List<LichSuTonResponse> list = lichSuTonService.getAllLichSuTon();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách lịch sử tồn thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{maLS}")
    public ResponseEntity<Map<String, Object>> getLichSuTon(@PathVariable Integer maLS) {
        log.info("Nhận yêu cầu lấy thông tin lịch sử tồn: {}", maLS);
        
        LichSuTonResponse lichSu = lichSuTonService.getLichSuTon(maLS);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin lịch sử tồn thành công");
        response.put("data", lichSu);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/drug/{maThuoc}")
    public ResponseEntity<Map<String, Object>> getLichSuByMaThuoc(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu lấy lịch sử tồn theo mã thuốc: {}", maThuoc);
        
        List<LichSuTonResponse> list = lichSuTonService.getLichSuByMaThuoc(maThuoc);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy lịch sử tồn theo mã thuốc thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<Map<String, Object>> getLichSuByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Nhận yêu cầu lấy lịch sử tồn từ {} đến {}", start, end);
        
        List<LichSuTonResponse> list = lichSuTonService.getLichSuByDateRange(start, end);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy lịch sử tồn theo khoảng thời gian thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<Map<String, Object>> getRecentLichSuTon() {
        log.info("Nhận yêu cầu lấy 10 lịch sử tồn gần nhất");
        
        List<LichSuTonResponse> list = lichSuTonService.getRecentLichSuTon();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy lịch sử tồn gần nhất thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{maLS}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteLichSuTon(@PathVariable Integer maLS) {
        log.info("Nhận yêu cầu xóa lịch sử tồn: {}", maLS);
        
        lichSuTonService.deleteLichSuTon(maLS);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa lịch sử tồn thành công");
        
        return ResponseEntity.ok(response);
    }
}
