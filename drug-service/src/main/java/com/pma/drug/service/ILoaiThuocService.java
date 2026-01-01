package com.pma.drug.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pma.drug.dto.LoaiThuocRequest;
import com.pma.drug.entity.LoaiThuoc;

public interface ILoaiThuocService {
	LoaiThuoc createLoaiThuoc(LoaiThuocRequest request);
	LoaiThuoc updateLoaiThuoc(String maLoai, LoaiThuocRequest request);
	LoaiThuoc getLoaiThuoc(String maLoai);
	List<LoaiThuoc> getAllLoaiThuoc();
	Page<LoaiThuoc> searchLoaiThuocByName(String tenLoai, int page, int size);
	void deleteLoaiThuoc(String maLoai);
}
