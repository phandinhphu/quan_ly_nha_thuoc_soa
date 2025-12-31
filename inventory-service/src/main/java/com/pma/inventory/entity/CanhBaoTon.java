package com.pma.inventory.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CanhBaoTon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanhBaoTon {
    
    @Id
    @Column(name = "MaThuoc", length = 20)
    private String maThuoc;
    
    @Column(name = "SoLuongToiThieu")
    private Integer soLuongToiThieu = 10;
    
    @Column(name = "SoLuongHienTai")
    private Integer soLuongHienTai;
    
    @Column(name = "TrangThai", length = 50)
    private String trangThai;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
}
