package com.pma.sales.repository;

import com.pma.sales.entity.ChiTietHoaDon;
import com.pma.sales.entity.ChiTietId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, ChiTietId> {
}

