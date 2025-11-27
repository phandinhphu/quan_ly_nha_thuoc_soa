package com.pma.drug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiThuocRequest {
    
    @NotBlank(message = "Mã loại không được để trống")
    private String maLoai;
    
    @NotBlank(message = "Tên loại không được để trống")
    private String tenLoai;
    
    private String moTa;
}
