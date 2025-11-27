package com.pma.drug.repository;

import com.pma.drug.entity.LoaiThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiThuocRepository extends JpaRepository<LoaiThuoc, String> {
    boolean existsByMaLoai(String maLoai);
}
