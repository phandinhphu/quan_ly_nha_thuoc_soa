package com.pma.drug.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pma.drug.dto.DonViTinhRequest;
import com.pma.drug.entity.DonViTinh;

public interface IDonViTinhService {
	DonViTinh createDonViTinh(DonViTinhRequest request);
	DonViTinh updateDonViTinh(String maDonVi, DonViTinhRequest request);
	DonViTinh getDonViTinh(String maDonVi);
	List<DonViTinh> getAllDonViTinh();
	Page<DonViTinh> searchDonViTinh(String keyword, int page, int size);
	void deleteDonViTinh(String maDonVi);
}
