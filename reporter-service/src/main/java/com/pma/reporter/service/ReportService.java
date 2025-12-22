package com.pma.reporter.service;

import com.pma.reporter.client.DrugClient;
import com.pma.reporter.client.SalesClient;
import com.pma.reporter.dto.DrugReportDTO;
import com.pma.reporter.dto.ReportSummaryDTO;
import com.pma.reporter.dto.SalesResponse;
import com.pma.reporter.dto.ApiResponse;
import com.pma.reporter.dto.DrugDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final SalesClient salesClient;
    private final DrugClient drugClient;

    public ReportSummaryDTO generateGeneralReport(String date, String token) {
        log.info("Bắt đầu tổng hợp báo cáo. Lọc tại chỗ cho ngày: {}", date);

        // 1. Vẫn lấy toàn bộ hóa đơn từ Sales Service (vì code Sales đã cố định)
        ApiResponse<List<SalesResponse>> response = salesClient.getAllHoaDon(token);

        if (response == null || !response.isSuccess() || response.getData() == null) {
            return createEmptyReport();
        }

        List<SalesResponse> allSales = response.getData();

        // 2. THỰC HIỆN LỌC DỮ LIỆU TẠI ĐÂY
        if (date != null && !date.isEmpty()) {
            allSales = allSales.stream()
                    .filter(sale -> {
                        // sale.getNgayBan() thường là LocalDateTime hoặc String
                        // Ta so sánh phần ngày (YYYY-MM-DD)
                        return sale.getNgayBan().toString().contains(date);
                    })
                    .collect(Collectors.toList());
        }

        // 3. Logic tính toán tổng doanh thu dựa trên danh sách đã lọc
        BigDecimal totalRevenue = allSales.stream()
                .map(SalesResponse::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Gom nhóm thuốc bán chạy (chỉ tính từ danh sách đã lọc)
        Map<String, Integer> drugCountMap = new HashMap<>();
        allSales.forEach(sale -> {
            if (sale.getChiTiet() != null) {
                sale.getChiTiet().forEach(item -> {
                    drugCountMap.put(item.getMaThuoc(), 
                        drugCountMap.getOrDefault(item.getMaThuoc(), 0) + item.getSoLuong());
                });
            }
        });

        // 5. Chuyển đổi sang DTO và lấy tên thuốc
        List<DrugReportDTO> reportDetails = drugCountMap.entrySet().stream()
                .map(entry -> {
                    String maThuoc = entry.getKey();
                    String tenThuoc = "N/A (Không tìm thấy)";
                    try {
                    // 1. Gọi API nhận về vỏ bọc ApiResponse
                    ApiResponse<DrugDTO> drugResponse = drugClient.getDrugById(maThuoc, token);
                    
                    // 2. Kiểm tra success và trích xuất data
                    if (drugResponse != null && drugResponse.isSuccess() && drugResponse.getData() != null) {
                        tenThuoc = drugResponse.getData().getTenThuoc(); // Lấy tên thuốc từ trường data
                    } else {
                        log.warn("Không tìm thấy thông tin thuốc cho mã: {}", maThuoc);
                    }
                } catch (Exception e) {
                    log.error("Lỗi gọi Drug-Service cho mã {}: {}", maThuoc, e.getMessage());
                }
                    return new DrugReportDTO(maThuoc, tenThuoc, entry.getValue());
                })
                .collect(Collectors.toList());

        return ReportSummaryDTO.builder()
            .totalInvoices((long) allSales.size())
            .totalRevenue(totalRevenue)
            .topSellingDrugs(reportDetails)
            .build();
    }

    private ReportSummaryDTO createEmptyReport() {
        return ReportSummaryDTO.builder()
                .totalInvoices(0L)
                .totalRevenue(BigDecimal.ZERO)
                .topSellingDrugs(new ArrayList<>())
                .build();
    }
}