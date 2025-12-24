package com.pma.reporter.client;
import com.pma.reporter.dto.ApiResponse;
import com.pma.reporter.dto.DrugDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "drug-service")
public interface DrugClient {
    @GetMapping("/api/drugs/{maThuoc}")
    // Phải bọc trong ApiResponse để lấy được trường "data" bên trong
    ApiResponse<DrugDTO> getDrugById(@PathVariable("maThuoc") String maThuoc, 
                                    @RequestHeader("Authorization") String token);
}