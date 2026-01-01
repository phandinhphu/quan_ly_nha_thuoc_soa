package com.pma.drug.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pma.drug.dto.ThuocRequest;
import com.pma.drug.dto.ThuocResponse;
import com.pma.drug.entity.Thuoc;

public interface IThuocService {
	ThuocResponse createThuoc(ThuocRequest request);
	ThuocResponse updateThuoc(String maThuoc, ThuocRequest request);
	ThuocResponse updateStock(String maThuoc, Integer quantity);
	ThuocResponse getThuoc(String maThuoc);
	List<ThuocResponse> getAllThuoc();
	Page<Thuoc> searchThuocByName(String tenThuoc, int page, int size);
	List<ThuocResponse> getThuocByLoai(String maLoai);
	List<ThuocResponse> getLowStockDrugs(Integer threshold);
	List<ThuocResponse> getExpiringDrugs(Integer daysAhead);
	void deleteThuoc(String maThuoc);
}
