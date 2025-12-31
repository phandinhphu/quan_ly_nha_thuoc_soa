package com.pma.inventory.dto;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
}
