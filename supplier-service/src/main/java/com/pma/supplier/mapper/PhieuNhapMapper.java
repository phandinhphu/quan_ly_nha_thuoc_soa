package com.pma.supplier.mapper;

import com.pma.supplier.dto.PhieuNhapResponse;
import com.pma.supplier.entity.PhieuNhap;

public class PhieuNhapMapper {
	
	public static PhieuNhapResponse toResponse(PhieuNhap phieuNhap) {
		PhieuNhapResponse response = new PhieuNhapResponse();
		response.setMaPhieuNhap(phieuNhap.getMaPhieuNhap());
		response.setNgayNhap(phieuNhap.getNgayNhap());
		response.setTongTien(phieuNhap.getTongTien());
		response.setMaNCC(phieuNhap.getMaNCC());
		response.setMaNV(phieuNhap.getMaNV());
		
		return response;
	}
	
}
