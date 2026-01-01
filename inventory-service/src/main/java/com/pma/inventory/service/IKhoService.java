package com.pma.inventory.service;

import java.util.List;

import com.pma.inventory.dto.KhoRequest;
import com.pma.inventory.dto.KhoResponse;

public interface IKhoService {
	KhoResponse createKho(KhoRequest request);
	List<KhoResponse> getAllKho();
	KhoResponse getKho(String maKho);
	KhoResponse updateKho(String maKho, KhoRequest request);
	void deleteKho(String maKho);
}
