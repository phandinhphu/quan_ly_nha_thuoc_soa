package com.pma.drug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThuocRequest {
    
    @NotBlank(message = "Mã thuốc không được để trống")
    private String maThuoc;
    
    @NotBlank(message = "Tên thuốc không được để trống")
    private String tenThuoc;
    
    @NotBlank(message = "Mã loại không được để trống")
    private String maLoai;
    
    @NotBlank(message = "Mã đơn vị không được để trống")
    private String maDonVi;
    
    @NotNull(message = "Giá nhập không được để trống")
    @DecimalMin(value = "0.0", message = "Giá nhập phải lớn hơn 0")
    private BigDecimal giaNhap;
    
    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", message = "Giá bán phải lớn hơn 0")
    private BigDecimal giaBan;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hanSuDung;
    
    private String nhaSanXuat;
    
    private Integer soLuongTon;
    
    private String moTa;
}
