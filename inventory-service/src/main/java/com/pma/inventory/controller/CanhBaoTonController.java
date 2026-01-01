package com.pma.inventory.controller;

import com.pma.inventory.dto.ApiResponse;
import com.pma.inventory.dto.CanhBaoTonRequest;
import com.pma.inventory.dto.CanhBaoTonResponse;
import com.pma.inventory.service.ICanhBaoTonService;

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
@RequestMapping("/api/stock-alerts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CanhBaoTonController {
    
    private final ICanhBaoTonService canhBaoTonService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<CanhBaoTonResponse>> createCanhBaoTon(@Valid @RequestBody CanhBaoTonRequest request) {
        log.info("Nhận yêu cầu tạo cảnh báo tồn cho thuốc: {}", request.getMaThuoc());
        
        CanhBaoTonResponse canhBao = canhBaoTonService.createCanhBaoTon(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(canhBao, "Tạo cảnh báo tồn thành công"));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CanhBaoTonResponse>>> getAllCanhBaoTon() {
        log.info("Nhận yêu cầu lấy danh sách cảnh báo tồn");
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getAllCanhBaoTon();
        
        return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách cảnh báo tồn thành công"));
    }
    
    @GetMapping("/{maThuoc}")
    public ResponseEntity<ApiResponse<CanhBaoTonResponse>> getCanhBaoTon(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu lấy thông tin cảnh báo tồn: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.getCanhBaoTon(maThuoc);
        
        return ResponseEntity.ok(ApiResponse.success(canhBao, "Lấy thông tin cảnh báo tồn thành công"));
    }
    
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<List<CanhBaoTonResponse>>> getLowStockAlerts() {
        log.info("Nhận yêu cầu lấy danh sách cảnh báo tồn thấp");
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getLowStockAlerts();
        
        return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách cảnh báo tồn thấp thành công"));
    }
    
    @GetMapping("/status/{trangThai}")
    public ResponseEntity<ApiResponse<List<CanhBaoTonResponse>>> getCanhBaoByTrangThai(@PathVariable String trangThai) {
        log.info("Nhận yêu cầu lấy cảnh báo tồn theo trạng thái: {}", trangThai);
        
        List<CanhBaoTonResponse> list = canhBaoTonService.getCanhBaoByTrangThai(trangThai);
        
        return ResponseEntity.ok(ApiResponse.success(list, "Lấy danh sách cảnh báo tồn theo trạng thái thành công"));
    }
    
    @PutMapping("/{maThuoc}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<CanhBaoTonResponse>> updateCanhBaoTon(
            @PathVariable String maThuoc,
            @Valid @RequestBody CanhBaoTonRequest request) {
        log.info("Nhận yêu cầu cập nhật cảnh báo tồn: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.updateCanhBaoTon(maThuoc, request);
        
        return ResponseEntity.ok(ApiResponse.success(canhBao, "Cập nhật cảnh báo tồn thành công"));
    }
    
    @PutMapping("/{maThuoc}/current-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<CanhBaoTonResponse>> updateSoLuongHienTai(
            @PathVariable String maThuoc,
            @RequestParam Integer soLuongHienTai) {
        log.info("Nhận yêu cầu cập nhật số lượng hiện tại cho thuốc: {}", maThuoc);
        
        CanhBaoTonResponse canhBao = canhBaoTonService.updateSoLuongHienTai(maThuoc, soLuongHienTai);
        
        return ResponseEntity.ok(ApiResponse.success(canhBao, "Cập nhật số lượng hiện tại thành công"));
    }
    
    @DeleteMapping("/{maThuoc}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCanhBaoTon(@PathVariable String maThuoc) {
        log.info("Nhận yêu cầu xóa cảnh báo tồn: {}", maThuoc);
        
        canhBaoTonService.deleteCanhBaoTon(maThuoc);
        
        return ResponseEntity.ok(ApiResponse.successWithoutData("Xóa cảnh báo tồn thành công"));
    }
}
