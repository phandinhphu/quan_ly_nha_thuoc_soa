package com.pma.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String tenDangNhap;
    private String maNV;
    private String hoTen;
    private String vaiTro;
    private String trangThai;
}
