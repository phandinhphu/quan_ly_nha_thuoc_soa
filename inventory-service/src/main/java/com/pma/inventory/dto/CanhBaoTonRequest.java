package com.pma.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanhBaoTonRequest {
    
    @NotBlank(message = "Mã thuốc không được để trống")
    private String maThuoc;
    
    @NotNull(message = "Số lượng tối thiểu không được để trống")
    private Integer soLuongToiThieu;
    
    private Integer soLuongHienTai;
    private String trangThai;
}
