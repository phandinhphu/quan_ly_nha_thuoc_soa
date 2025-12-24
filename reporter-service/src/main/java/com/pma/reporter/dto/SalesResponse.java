package com.pma.reporter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponse {
    private String maHoaDon;
    private LocalDate ngayBan;
    private String maNV;
    private BigDecimal tongTien;
    private List<ChiTietSalesDTO> chiTiet;
}