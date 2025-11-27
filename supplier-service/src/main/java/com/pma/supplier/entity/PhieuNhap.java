package com.pma.supplier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PhieuNhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhap {
    @Id
    @Column(name = "MaPhieuNhap", length = 20)
    private String maPhieuNhap;

    @Column(name = "NgayNhap")
    private LocalDateTime ngayNhap;

    @Column(name = "MaNCC", length = 20)
    private String maNCC;

    @Column(name = "MaNV", length = 20)
    private String maNV;

    @Column(name = "TongTien", precision = 14, scale = 2)
    private BigDecimal tongTien;

    @OneToMany(mappedBy = "phieuNhap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietPhieuNhap> chiTiet = new ArrayList<>();
}
