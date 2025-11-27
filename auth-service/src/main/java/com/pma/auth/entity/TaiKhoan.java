package com.pma.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "TaiKhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    
    @Id
    @Column(name = "TenDangNhap", length = 50)
    private String tenDangNhap;
    
    @Column(name = "MatKhau", nullable = false, length = 255)
    private String matKhau;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNV", referencedColumnName = "MaNV")
    private NhanVien nhanVien;
    
    @Column(name = "TrangThai", length = 20)
    private String trangThai; // ACTIVE, INACTIVE, LOCKED
}
