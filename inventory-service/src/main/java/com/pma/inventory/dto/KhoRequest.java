package com.pma.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhoRequest {
    
    @NotBlank(message = "Mã kho không được để trống")
    private String maKho;
    
    @NotBlank(message = "Tên kho không được để trống")
    private String tenKho;
    
    private String diaChi;
    private String moTa;
}
