package com.pma.reporter.client;
import com.pma.reporter.dto.ApiResponse;
import com.pma.reporter.dto.SalesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "sales-service")
public interface SalesClient {
    @GetMapping("/api/sales/invoices")
    // Chỉnh type trả về từ List thành ApiResponse<List<...>>
    ApiResponse<List<SalesResponse>> getAllHoaDon(@RequestHeader("Authorization") String token);
}