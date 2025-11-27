package com.pma.inventory.repository;

import com.pma.inventory.entity.Kho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhoRepository extends JpaRepository<Kho, String> {
    Optional<Kho> findByTenKhoContainingIgnoreCase(String tenKho);
}
