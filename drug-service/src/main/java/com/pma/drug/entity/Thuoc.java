package com.pma.drug.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Thuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thuoc {
    
    @Id
    @Column(name = "MaThuoc", length = 20)
    private String maThuoc;
    
    @Column(name = "TenThuoc", nullable = false, length = 100)
    private String tenThuoc;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaLoai", referencedColumnName = "MaLoai")
    private LoaiThuoc loaiThuoc;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaDonVi", referencedColumnName = "MaDonVi")
    private DonViTinh donViTinh;
    
    @Column(name = "GiaNhap", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaNhap;
    
    @Column(name = "GiaBan", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaBan;
    
    @Column(name = "HanSuDung")
    private LocalDate hanSuDung;
    
    @Column(name = "NhaSanXuat", length = 100)
    private String nhaSanXuat;
    
    @Column(name = "SoLuongTon")
    private Integer soLuongTon = 0;
    
    @Column(name = "MoTa", length = 255)
    private String moTa;
}
