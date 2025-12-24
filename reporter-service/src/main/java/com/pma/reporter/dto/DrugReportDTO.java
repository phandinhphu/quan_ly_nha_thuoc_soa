package com.pma.reporter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Để tạo được đối tượng: new DrugReportDTO(ma, ten, sl)
@NoArgsConstructor
public class DrugReportDTO {
    private String maThuoc;
    private String tenThuoc;
    private Integer soLuongDaBan;
}