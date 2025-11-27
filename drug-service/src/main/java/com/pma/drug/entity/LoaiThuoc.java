package com.pma.drug.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "LoaiThuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiThuoc {
    
    @Id
    @Column(name = "MaLoai", length = 20)
    private String maLoai;
    
    @Column(name = "TenLoai", nullable = false, length = 100)
    private String tenLoai;
    
    @Column(name = "MoTa", length = 255)
    private String moTa;
}
