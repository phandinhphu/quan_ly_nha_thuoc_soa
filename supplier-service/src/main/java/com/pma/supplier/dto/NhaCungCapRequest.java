package com.pma.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCapRequest {
    @NotBlank(message = "Mã NCC không được để trống")
    private String maNCC;

    @NotBlank(message = "Tên NCC không được để trống")
    private String tenNCC;

    private String diaChi;
    private String soDienThoai;
    private String email;
    private String nguoiDaiDien;
}
