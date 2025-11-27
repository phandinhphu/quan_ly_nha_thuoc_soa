package com.pma.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuTon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuTon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLS")
    private Integer maLS;
    
    @Column(name = "MaThuoc", length = 20)
    private String maThuoc;
    
    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat;
    
    @Column(name = "SoLuongThayDoi")
    private Integer soLuongThayDoi;
    
    @Column(name = "LyDo", length = 255)
    private String lyDo;
}
