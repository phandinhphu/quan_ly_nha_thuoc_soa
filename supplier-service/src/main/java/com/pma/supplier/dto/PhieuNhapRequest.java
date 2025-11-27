package com.pma.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhapRequest {
    @NotBlank(message = "Mã phiếu nhập không được để trống")
    private String maPhieuNhap;

    @NotNull(message = "Ngày nhập không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayNhap;

    @NotBlank(message = "Mã nhà cung cấp không được để trống")
    private String maNCC;

    @NotBlank(message = "Mã nhân viên không được để trống")
    private String maNV;

    @NotNull(message = "Danh sách chi tiết không được để trống")
    private List<ChiTietRequest> chiTiet;
}
