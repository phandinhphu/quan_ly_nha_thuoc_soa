package com.pma.drug.mapper;

import com.pma.drug.dto.ThuocResponse;
import com.pma.drug.entity.Thuoc;

public class ThuocMapper {
	public static ThuocResponse toResponse(Thuoc thuoc) {
		ThuocResponse response = new ThuocResponse();
		response.setMaThuoc(thuoc.getMaThuoc());
		response.setTenThuoc(thuoc.getTenThuoc());
		response.setMaLoai(thuoc.getLoaiThuoc().getMaLoai());
		response.setTenLoai(thuoc.getLoaiThuoc().getTenLoai());
		response.setMaDonVi(thuoc.getDonViTinh().getMaDonVi());
		response.setTenDonVi(thuoc.getDonViTinh().getTenDonVi());
		response.setGiaNhap(thuoc.getGiaNhap());
		response.setGiaBan(thuoc.getGiaBan());
		response.setHanSuDung(thuoc.getHanSuDung());
		response.setNhaSanXuat(thuoc.getNhaSanXuat());
		response.setSoLuongTon(thuoc.getSoLuongTon());
		response.setMoTa(thuoc.getMoTa());
		return response;
	}
}
