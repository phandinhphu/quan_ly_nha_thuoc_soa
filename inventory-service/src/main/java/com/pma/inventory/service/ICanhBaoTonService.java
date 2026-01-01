package com.pma.inventory.service;

import java.util.List;

import com.pma.inventory.dto.CanhBaoTonRequest;
import com.pma.inventory.dto.CanhBaoTonResponse;

public interface ICanhBaoTonService {
	CanhBaoTonResponse createCanhBaoTon(CanhBaoTonRequest request);
	List<CanhBaoTonResponse> getAllCanhBaoTon();
	CanhBaoTonResponse getCanhBaoTon(String maThuoc);
	List<CanhBaoTonResponse> getLowStockAlerts();
	List<CanhBaoTonResponse> getCanhBaoByTrangThai(String trangThai);
	CanhBaoTonResponse updateCanhBaoTon(String maThuoc, CanhBaoTonRequest request);
	CanhBaoTonResponse updateSoLuongHienTai(String maThuoc, Integer soLuongHienTai);
	void deleteCanhBaoTon(String maThuoc);
}
