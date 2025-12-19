package com.pma.sales.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietHoaDon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChiTietId.class)
public class ChiTietHoaDon {
    @Id
    @Column(name = "MaHoaDon", length = 20)
    private String maHoaDon;

    @Id
    @Column(name = "MaThuoc", length = 20)
    private String maThuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHoaDon", insertable = false, updatable = false)
    private HoaDon hoaDon;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 12, scale = 2)
    private BigDecimal donGia;

    @Column(name = "ThanhTien", precision = 12, scale = 2)
    private BigDecimal thanhTien;
}

