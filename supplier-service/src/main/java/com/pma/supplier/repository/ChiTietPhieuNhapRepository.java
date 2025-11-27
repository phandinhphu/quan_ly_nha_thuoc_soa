package com.pma.supplier.repository;

import com.pma.supplier.entity.ChiTietPhieuNhap;
import com.pma.supplier.entity.ChiTietId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuNhapRepository extends JpaRepository<ChiTietPhieuNhap, ChiTietId> {
}
