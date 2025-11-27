package com.pma.drug.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "DonViTinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh {
    
    @Id
    @Column(name = "MaDonVi", length = 20)
    private String maDonVi;
    
    @Column(name = "TenDonVi", length = 50)
    private String tenDonVi;
}
