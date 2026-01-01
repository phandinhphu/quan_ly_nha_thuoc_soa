package com.pma.inventory.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pma.inventory.dto.LichSuTonRequest;
import com.pma.inventory.dto.LichSuTonResponse;

public interface ILichSuTonService {
	LichSuTonResponse createLichSuTon(LichSuTonRequest request);
	List<LichSuTonResponse> getAllLichSuTon();
	LichSuTonResponse getLichSuTon(Integer maLS);
	List<LichSuTonResponse> getLichSuByMaThuoc(String maThuoc);
	List<LichSuTonResponse> getLichSuByDateRange(LocalDateTime start, LocalDateTime end);
	List<LichSuTonResponse> getRecentLichSuTon();
	void deleteLichSuTon(Integer maLS);
}
