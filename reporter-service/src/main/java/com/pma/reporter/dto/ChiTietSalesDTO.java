package com.pma.reporter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSalesDTO {
    private String maThuoc;
    private Integer soLuong;
    private BigDecimal donGia;
}