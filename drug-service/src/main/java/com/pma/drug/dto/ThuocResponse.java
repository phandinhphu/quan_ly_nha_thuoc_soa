package com.pma.drug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThuocResponse {
    private String maThuoc;
    private String tenThuoc;
    private String maLoai;
    private String tenLoai;
    private String maDonVi;
    private String tenDonVi;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hanSuDung;
    private String nhaSanXuat;
    private Integer soLuongTon;
    private String moTa;
}
