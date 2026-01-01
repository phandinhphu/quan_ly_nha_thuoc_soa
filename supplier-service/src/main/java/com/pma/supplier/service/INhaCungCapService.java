package com.pma.supplier.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pma.supplier.dto.NhaCungCapRequest;
import com.pma.supplier.entity.NhaCungCap;

public interface INhaCungCapService {
	NhaCungCap createNhaCungCap(NhaCungCapRequest request);
	NhaCungCap getNhaCungCap(String maNCC);
	List<NhaCungCap> getAllNhaCungCap();
	NhaCungCap updateNhaCungCap(String maNCC, NhaCungCapRequest request);
	Page<NhaCungCap> getNhaCungCapByPage(int page, int size);
	void deleteNhaCungCap(String maNCC);
}
