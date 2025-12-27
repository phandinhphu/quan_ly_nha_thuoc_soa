package com.pma.inventory.controller;

import com.pma.inventory.dto.CanhBaoTonRequest;
import com.pma.inventory.dto.CanhBaoTonResponse;
import com.pma.inventory.service.CanhBaoTonService;
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
@RequestMapping("/api/stock-alerts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CanhBaoTonController {
    
    private final CanhBaoTonService canhBaoTonService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> createCanhBaoTon(@Valid @RequestBody CanhBaoTonRequest request) {
        log.info("Nhận yêu cầu tạo cảnh báo tồn cho thuốc: {}", request.getMaThuoc());
        
        CanhBaoTonResponse canhBao = canhBaoTonService.createCanhBaoTon(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo cảnh báo tồn thành công");
        response.put("data", canhBao);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCanhBaoTon() {
        log.info("Nhận yêu cầu lấy danh sách cảnh báo tồn");
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getAllCanhBaoTon();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách cảnh báo tồn thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{maThuoc}")
    public ResponseEntity<Map<String, Object>> getCanhBaoTon(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu lấy thông tin cảnh báo tồn: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.getCanhBaoTon(maThuoc);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin cảnh báo tồn thành công");
        response.put("data", canhBao);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getLowStockAlerts() {
        log.info("Nhận yêu cầu lấy danh sách cảnh báo tồn thấp");
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getLowStockAlerts();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách cảnh báo tồn thấp thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{trangThai}")
    public ResponseEntity<Map<String, Object>> getCanhBaoByTrangThai(@PathVariable String trangThai) {
        log.info("Nhận yêu cầu lấy cảnh báo tồn theo trạng thái: {}", trangThai);
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getCanhBaoByTrangThai(trangThai);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy cảnh báo tồn theo trạng thái thành công");
        response.put("data", list);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{maThuoc}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateCanhBaoTon(
            @PathVariable String maThuoc,
            @Valid @RequestBody CanhBaoTonRequest request) {
        log.info("Nhận yêu cầu cập nhật cảnh báo tồn: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.updateCanhBaoTon(maThuoc, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật cảnh báo tồn thành công");
        response.put("data", canhBao);
        
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{maThuoc}/current-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateSoLuongHienTai(
            @PathVariable String maThuoc,
            @RequestParam Integer soLuongHienTai) {
        log.info("Nhận yêu cầu cập nhật số lượng hiện tại cho thuốc: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.updateSoLuongHienTai(maThuoc, soLuongHienTai);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật số lượng hiện tại thành công");
        response.put("data", canhBao);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{maThuoc}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteCanhBaoTon(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu xóa cảnh báo tồn: {}", maThuoc);
        
        canhBaoTonService.deleteCanhBaoTon(maThuoc);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa cảnh báo tồn thành công");
        
        return ResponseEntity.ok(response);
    }
}
