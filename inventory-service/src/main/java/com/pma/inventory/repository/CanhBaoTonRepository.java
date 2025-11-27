package com.pma.inventory.repository;

import com.pma.inventory.entity.CanhBaoTon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanhBaoTonRepository extends JpaRepository<CanhBaoTon, String> {
    @Query("SELECT c FROM CanhBaoTon c WHERE c.soLuongHienTai <= c.soLuongToiThieu")
    List<CanhBaoTon> findLowStockAlerts();
    
    List<CanhBaoTon> findByTrangThai(String trangThai);
}
