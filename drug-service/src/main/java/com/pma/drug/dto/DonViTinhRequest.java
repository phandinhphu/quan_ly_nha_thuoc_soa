package com.pma.drug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinhRequest {
    
    @NotBlank(message = "Mã đơn vị không được để trống")
    private String maDonVi;
    
    @NotBlank(message = "Tên đơn vị không được để trống")
    private String tenDonVi;
}
