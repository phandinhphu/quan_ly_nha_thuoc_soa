package com.pma.sales.dto;

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
public class HoaDonRequest {
    @NotBlank(message = "Mã hóa đơn không được để trống")
    private String maHoaDon;

    @NotNull(message = "Ngày bán không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayBan;

    @NotBlank(message = "Mã nhân viên không được để trống")
    private String maNV;

    @NotNull(message = "Danh sách chi tiết không được để trống")
    private List<ChiTietRequest> chiTiet;
}

