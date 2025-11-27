package com.pma.supplier.controller;

import com.pma.supplier.dto.NhaCungCapRequest;
import com.pma.supplier.entity.NhaCungCap;
import com.pma.supplier.service.NhaCungCapService;
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
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Validated
@Slf4j
public class NhaCungCapController {
    private final NhaCungCapService nhaCungCapService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> createNhaCungCap(@Valid @RequestBody NhaCungCapRequest request) {
        log.info("Nhận yêu cầu tạo nhà cung cấp: {}", request.getMaNCC());

        NhaCungCap ncc = nhaCungCapService.createNhaCungCap(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo nhà cung cấp thành công");
        response.put("data", ncc);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNhaCungCap() {
        log.info("Nhận yêu cầu lấy danh sách nhà cung cấp");

        List<NhaCungCap> list = nhaCungCapService.getAllNhaCungCap();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách nhà cung cấp thành công");
        response.put("data", list);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{maNCC}")
    public ResponseEntity<Map<String, Object>> getNhaCungCap(@PathVariable String maNCC) {
        log.info("Nhận yêu cầu lấy thông tin nhà cung cấp: {}", maNCC);

        NhaCungCap ncc = nhaCungCapService.getNhaCungCap(maNCC);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy thông tin nhà cung cấp thành công");
        response.put("data", ncc);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{maNCC}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> updateNhaCungCap(
            @PathVariable String maNCC,
            @Valid @RequestBody NhaCungCapRequest request) {
        log.info("Nhận yêu cầu cập nhật nhà cung cấp: {}", maNCC);

        NhaCungCap ncc = nhaCungCapService.updateNhaCungCap(maNCC, request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật nhà cung cấp thành công");
        response.put("data", ncc);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{maNCC}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteNhaCungCap(@PathVariable String maNCC) {
        log.info("Nhận yêu cầu xóa nhà cung cấp: {}", maNCC);

        nhaCungCapService.deleteNhaCungCap(maNCC);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa nhà cung cấp thành công");

        return ResponseEntity.ok(response);
    }
}
