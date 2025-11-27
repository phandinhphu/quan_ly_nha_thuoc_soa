package com.pma.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuTonRequest {
    
    @NotBlank(message = "Mã thuốc không được để trống")
    private String maThuoc;
    
    @NotNull(message = "Số lượng thay đổi không được để trống")
    private Integer soLuongThayDoi;
    
    @NotBlank(message = "Lý do không được để trống")
    private String lyDo;
}
