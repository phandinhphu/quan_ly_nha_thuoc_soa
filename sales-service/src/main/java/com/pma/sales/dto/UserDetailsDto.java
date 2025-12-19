package com.pma.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String tenDangNhap;
    private String maNV;
    private String hoTen;
    private String vaiTro;
    private String trangThai;
}

