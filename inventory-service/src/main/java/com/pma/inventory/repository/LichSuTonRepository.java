package com.pma.inventory.repository;

import com.pma.inventory.entity.LichSuTon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LichSuTonRepository extends JpaRepository<LichSuTon, Integer> {
    List<LichSuTon> findByMaThuocOrderByNgayCapNhatDesc(String maThuoc);
    List<LichSuTon> findByNgayCapNhatBetweenOrderByNgayCapNhatDesc(LocalDateTime start, LocalDateTime end);
    List<LichSuTon> findTop10ByOrderByNgayCapNhatDesc();
}
