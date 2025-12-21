package com.pma.drug.repository;

import com.pma.drug.entity.DonViTinh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonViTinhRepository extends JpaRepository<DonViTinh, String> {
    boolean existsByMaDonVi(String maDonVi);
    Page<DonViTinh> findByTenDonViContainingIgnoreCase(String tenDonVi, Pageable pageable);
}
