package com.pma.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhoResponse {
    private String maKho;
    private String tenKho;
    private String diaChi;
    private String moTa;
}
