package com.pma.supplier.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pma.supplier.dto.PhieuNhapRequest;
import com.pma.supplier.dto.PhieuNhapResponse;

public interface IPhieuNhapService {
	PhieuNhapResponse createPhieuNhap(PhieuNhapRequest request, String token);
	Page<PhieuNhapResponse> getPhieuNhapByPage(int page, int size);
	PhieuNhapResponse getPhieuNhap(String maPhieuNhap);
	List<PhieuNhapResponse> getAllPhieuNhap();
	void deletePhieuNhap(String maPhieuNhap);
}
