package com.pma.supplier.service;

import com.pma.supplier.client.DrugServiceClient;
import com.pma.supplier.client.InventoryServiceClient;
import com.pma.supplier.dto.ChiTietRequest;
import com.pma.supplier.dto.PhieuNhapRequest;
import com.pma.supplier.dto.PhieuNhapResponse;
import com.pma.supplier.entity.ChiTietPhieuNhap;
import com.pma.supplier.entity.PhieuNhap;
import com.pma.supplier.exception.ResourceNotFoundException;
import com.pma.supplier.exception.ValidationException;
import com.pma.supplier.mapper.PhieuNhapMapper;
import com.pma.supplier.repository.NhaCungCapRepository;
import com.pma.supplier.repository.PhieuNhapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhieuNhapService {
	private final PhieuNhapRepository phieuNhapRepository;
	private final NhaCungCapRepository nhaCungCapRepository;
	private final DrugServiceClient drugServiceClient;
	private final InventoryServiceClient inventoryServiceClient;

	@Transactional
	public PhieuNhapResponse createPhieuNhap(PhieuNhapRequest request, String token) {
		log.info("Đang tạo phiếu nhập mới: {}", request.getMaPhieuNhap());

		// Validate
		if (phieuNhapRepository.existsById(request.getMaPhieuNhap())) {
			throw new ValidationException("Mã phiếu nhập đã tồn tại");
		}

		// Kiểm tra nhà cung cấp tồn tại
		nhaCungCapRepository.findById(request.getMaNCC()).orElseThrow(
				() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với mã: " + request.getMaNCC()));

		// Kiểm tra tất cả mã thuốc có tồn tại không
		for (ChiTietRequest ct : request.getChiTiet()) {
			boolean drugExists = drugServiceClient.checkDrugExists(ct.getMaThuoc(), token);
			if (!drugExists) {
				throw new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + ct.getMaThuoc());
			}
		}
		log.info("Đã kiểm tra tất cả mã thuốc, tất cả đều tồn tại");

		// Tạo phiếu nhập
		PhieuNhap phieuNhap = new PhieuNhap();
		phieuNhap.setMaPhieuNhap(request.getMaPhieuNhap());
		phieuNhap.setNgayNhap(request.getNgayNhap());
		phieuNhap.setMaNCC(request.getMaNCC());
		phieuNhap.setMaNV(request.getMaNV());

		// Tính tổng tiền và tạo chi tiết
		BigDecimal tongTien = BigDecimal.ZERO;
		for (ChiTietRequest ct : request.getChiTiet()) {
			ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap();
			chiTiet.setMaPhieuNhap(request.getMaPhieuNhap());
			chiTiet.setMaThuoc(ct.getMaThuoc());
			chiTiet.setSoLuong(ct.getSoLuong());
			chiTiet.setDonGia(ct.getDonGia());

			BigDecimal thanhTien = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()));
			chiTiet.setThanhTien(thanhTien);
			tongTien = tongTien.add(thanhTien);

			phieuNhap.getChiTiet().add(chiTiet);
		}

		phieuNhap.setTongTien(tongTien);

		// Lưu phiếu nhập
		phieuNhap = phieuNhapRepository.save(phieuNhap);

		// Cập nhật tồn kho qua drug-service và tạo lịch sử tồn qua inventory-service
		for (ChiTietRequest ct : request.getChiTiet()) {
			// Cập nhật tồn kho trong drug-service
			boolean stockUpdated = drugServiceClient.updateStock(ct.getMaThuoc(), ct.getSoLuong(), token);
			if (!stockUpdated) {
				log.warn("Không thể cập nhật tồn kho cho thuốc: {}", ct.getMaThuoc());
			}

			// Tạo lịch sử tồn trong inventory-service
			String lyDo = "Nhập hàng từ phiếu nhập " + request.getMaPhieuNhap();
			boolean historyCreated = inventoryServiceClient.createInventoryHistory(ct.getMaThuoc(), ct.getSoLuong(),
					lyDo, token);
			if (!historyCreated) {
				log.warn("Không thể tạo lịch sử tồn kho cho thuốc: {}", ct.getMaThuoc());
			}
		}

		log.info("Tạo phiếu nhập thành công: {}", request.getMaPhieuNhap());

		// Trả về response
		PhieuNhapResponse response = PhieuNhapMapper.toResponse(phieuNhap);
		response.setChiTiet(request.getChiTiet());

		return response;
	}

	@Transactional(readOnly = true)
	public Page<PhieuNhapResponse> getPhieuNhapByPage(int page, int size) {
		log.info("Đang lấy danh sách phiếu nhập trang: {}, kích thước: {}", page, size);
		Page<PhieuNhap> phieuNhapPage = phieuNhapRepository
				.findAll(PageRequest.of(page, size));

		return phieuNhapPage.map(phieuNhap -> {
			PhieuNhapResponse response = PhieuNhapMapper.toResponse(phieuNhap);

			List<ChiTietRequest> chiTiet = phieuNhap.getChiTiet().stream()
					.map(ct -> new ChiTietRequest(ct.getMaThuoc(), ct.getSoLuong(), ct.getDonGia()))
					.collect(Collectors.toList());
			response.setChiTiet(chiTiet);

			return response;
		});
	}

	@Transactional(readOnly = true)
	public PhieuNhapResponse getPhieuNhap(String maPhieuNhap) {
		PhieuNhap phieuNhap = phieuNhapRepository.findById(maPhieuNhap)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu nhập với mã: " + maPhieuNhap));

		PhieuNhapResponse response = PhieuNhapMapper.toResponse(phieuNhap);

		// Convert chi tiết
		List<ChiTietRequest> chiTiet = phieuNhap.getChiTiet().stream()
				.map(ct -> new ChiTietRequest(ct.getMaThuoc(), ct.getSoLuong(), ct.getDonGia()))
				.collect(Collectors.toList());
		response.setChiTiet(chiTiet);

		return response;
	}

	@Transactional(readOnly = true)
	public List<PhieuNhapResponse> getAllPhieuNhap() {
		return phieuNhapRepository.findAll().stream().map(phieuNhap -> {
			PhieuNhapResponse response = PhieuNhapMapper.toResponse(phieuNhap);

			List<ChiTietRequest> chiTiet = phieuNhap.getChiTiet().stream()
					.map(ct -> new ChiTietRequest(ct.getMaThuoc(), ct.getSoLuong(), ct.getDonGia()))
					.collect(Collectors.toList());
			response.setChiTiet(chiTiet);

			return response;
		}).collect(Collectors.toList());
	}

	@Transactional
	public void deletePhieuNhap(String maPhieuNhap) {
		log.info("Đang xóa phiếu nhập: {}", maPhieuNhap);

		if (!phieuNhapRepository.existsById(maPhieuNhap)) {
			throw new ResourceNotFoundException("Không tìm thấy phiếu nhập với mã: " + maPhieuNhap);
		}

		phieuNhapRepository.deleteById(maPhieuNhap);
	}
}
