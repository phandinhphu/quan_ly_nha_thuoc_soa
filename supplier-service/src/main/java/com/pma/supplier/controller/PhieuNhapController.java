package com.pma.supplier.controller;

import com.pma.supplier.dto.PhieuNhapRequest;
import com.pma.supplier.dto.PhieuNhapResponse;
import com.pma.supplier.service.PhieuNhapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/suppliers/receipts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PhieuNhapController {
    private final PhieuNhapService phieuNhapService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> createPhieuNhap(
            @Valid @RequestBody PhieuNhapRequest request,
            @RequestHeader(value = "Authorization") String authHeader) {
        log.info("Nhận yêu cầu tạo phiếu nhập: {}", request.getMaPhieuNhap());

        // Extract token từ header
        String token = authHeader.substring(7); // Bỏ "Bearer "

        PhieuNhapResponse phieuNhap = phieuNhapService.createPhieuNhap(request, token);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo phiếu nhập thành công và đã cập nhật tồn kho");
        response.put("data", phieuNhap);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPhieuNhap() {
        log.info("Nhận yêu cầu lấy danh sách phiếu nhập");

        List<PhieuNhapResponse> list = phieuNhapService.getAllPhieuNhap();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách phiếu nhập thành công");
        response.put("data", list);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{maPhieuNhap}")
    public ResponseEntity<Map<String, Object>> getPhieuNhap(@PathVariable String maPhieuNhap) {
        log.info("Nhận yêu cầu lấy thông tin phiếu nhập: {}", maPhieuNhap);

        PhieuNhapResponse phieuNhap = phieuNhapService.getPhieuNhap(maPhieuNhap);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin phiếu nhập thành công");
        response.put("data", phieuNhap);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{maPhieuNhap}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deletePhieuNhap(@PathVariable String maPhieuNhap) {
        log.info("Nhận yêu cầu xóa phiếu nhập: {}", maPhieuNhap);

        phieuNhapService.deletePhieuNhap(maPhieuNhap);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa phiếu nhập thành công");

        return ResponseEntity.ok(response);
    }
}
