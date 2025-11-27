package com.pma.supplier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietPhieuNhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChiTietId.class)
public class ChiTietPhieuNhap {
    @Id
    @Column(name = "MaPhieuNhap", length = 20)
    private String maPhieuNhap;

    @Id
    @Column(name = "MaThuoc", length = 20)
    private String maThuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhieuNhap", insertable = false, updatable = false)
    private PhieuNhap phieuNhap;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 12, scale = 2)
    private BigDecimal donGia;

    @Column(name = "ThanhTien", precision = 12, scale = 2)
    private BigDecimal thanhTien;
}
