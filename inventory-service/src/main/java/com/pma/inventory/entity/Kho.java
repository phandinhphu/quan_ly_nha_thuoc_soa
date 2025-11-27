package com.pma.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Kho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kho {
    
    @Id
    @Column(name = "MaKho", length = 20)
    private String maKho;
    
    @Column(name = "TenKho", nullable = false, length = 100)
    private String tenKho;
    
    @Column(name = "DiaChi", length = 200)
    private String diaChi;
    
    @Column(name = "MoTa", length = 255)
    private String moTa;
}
