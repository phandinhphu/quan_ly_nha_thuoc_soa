package com.pma.sales.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HoaDon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
    @Column(name = "MaHoaDon", length = 20)
    private String maHoaDon;

    @Column(name = "NgayBan")
    private LocalDateTime ngayBan;

    @Column(name = "MaNV", length = 20)
    private String maNV;

    @Column(name = "TongTien", precision = 14, scale = 2)
    private BigDecimal tongTien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietHoaDon> chiTiet = new ArrayList<>();
}

