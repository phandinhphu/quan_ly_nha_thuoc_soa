package com.pma.drug.repository;

import com.pma.drug.entity.Thuoc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ThuocRepository extends JpaRepository<Thuoc, String> {
    boolean existsByMaThuoc(String maThuoc);
    
    Page<Thuoc> findByTenThuocContainingIgnoreCase(String tenThuoc, Pageable pageable);
    
    List<Thuoc> findByLoaiThuoc_MaLoai(String maLoai);
    
    @Query("SELECT t FROM Thuoc t WHERE t.soLuongTon <= :ngayConh")
    List<Thuoc> findLowStockDrugs(@Param("ngayConh") Integer ngayConh);
    
    @Query("SELECT t FROM Thuoc t WHERE t.hanSuDung <= :date")
    List<Thuoc> findExpiringDrugs(@Param("date") LocalDate date);
}
