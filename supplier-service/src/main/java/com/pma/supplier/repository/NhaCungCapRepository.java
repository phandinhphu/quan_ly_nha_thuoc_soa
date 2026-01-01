package com.pma.supplier.repository;

import com.pma.supplier.entity.NhaCungCap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, String> {
}
