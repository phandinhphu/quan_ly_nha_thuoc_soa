package com.pma.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanhBaoTonResponse {
    private String maThuoc;
    private Integer soLuongToiThieu;
    private Integer soLuongHienTai;
    private String trangThai;
}
