package com.pma.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "NhanVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    
    @Id
    @Column(name = "MaNV", length = 20)
    private String maNV;
    
    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;
    
    @Column(name = "GioiTinh", length = 10)
    private String gioiTinh;
    
    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;
    
    @Column(name = "SoDienThoai", length = 15)
    private String soDienThoai;
    
    @Column(name = "DiaChi", length = 200)
    private String diaChi;
    
    @Column(name = "VaiTro", length = 50)
    private String vaiTro;
}
