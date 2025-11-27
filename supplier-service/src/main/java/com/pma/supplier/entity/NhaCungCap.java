package com.pma.supplier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NhaCungCap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {
    @Id
    @Column(name = "MaNCC", length = 20)
    private String maNCC;

    @Column(name = "TenNCC", nullable = false, length = 100)
    private String tenNCC;

    @Column(name = "DiaChi", length = 200)
    private String diaChi;

    @Column(name = "SoDienThoai", length = 15)
    private String soDienThoai;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "NguoiDaiDien", length = 100)
    private String nguoiDaiDien;
}
