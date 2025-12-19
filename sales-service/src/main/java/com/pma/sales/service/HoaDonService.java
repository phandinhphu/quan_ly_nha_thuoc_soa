package com.pma.sales.service;

import com.pma.sales.client.DrugServiceClient;
import com.pma.sales.client.InventoryServiceClient;
import com.pma.sales.dto.ChiTietRequest;
import com.pma.sales.dto.HoaDonRequest;
import com.pma.sales.dto.HoaDonResponse;
import com.pma.sales.entity.ChiTietHoaDon;
import com.pma.sales.entity.HoaDon;
import com.pma.sales.exception.ResourceNotFoundException;
import com.pma.sales.exception.ValidationException;
import com.pma.sales.repository.ChiTietHoaDonRepository;
import com.pma.sales.repository.HoaDonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HoaDonService {
    private final HoaDonRepository hoaDonRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;
    private final DrugServiceClient drugServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    @Transactional
    public HoaDonResponse createHoaDon(HoaDonRequest request, String token) {
        log.info("Đang tạo hóa đơn mới: {}", request.getMaHoaDon());

        // Validate
        if (hoaDonRepository.existsById(request.getMaHoaDon())) {
            throw new ValidationException("Mã hóa đơn đã tồn tại");
        }

        // Kiểm tra tất cả mã thuốc có tồn tại không và số lượng có đủ không
        for (ChiTietRequest ct : request.getChiTiet()) {
            boolean drugExists = drugServiceClient.checkDrugExists(ct.getMaThuoc(), token);
            if (!drugExists) {
                throw new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + ct.getMaThuoc());
            }

            boolean stockAvailable = drugServiceClient.checkStockAvailable(ct.getMaThuoc(), ct.getSoLuong(), token);
            if (!stockAvailable) {
                throw new ValidationException("Thuốc " + ct.getMaThuoc() + " không đủ số lượng trong kho");
            }
        }
        log.info("Đã kiểm tra tất cả mã thuốc và số lượng, tất cả đều hợp lệ");

        // Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(request.getMaHoaDon());
        hoaDon.setNgayBan(request.getNgayBan());
        hoaDon.setMaNV(request.getMaNV());

        // Tính tổng tiền và tạo chi tiết
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietRequest ct : request.getChiTiet()) {
            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setMaHoaDon(request.getMaHoaDon());
            chiTiet.setMaThuoc(ct.getMaThuoc());
            chiTiet.setSoLuong(ct.getSoLuong());
            chiTiet.setDonGia(ct.getDonGia());

            BigDecimal thanhTien = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()));
            chiTiet.setThanhTien(thanhTien);
            tongTien = tongTien.add(thanhTien);

            hoaDon.getChiTiet().add(chiTiet);
        }

        hoaDon.setTongTien(tongTien);

        // Lưu hóa đơn
        hoaDon = hoaDonRepository.save(hoaDon);

        // Giảm tồn kho qua drug-service và tạo lịch sử tồn qua inventory-service
        for (ChiTietRequest ct : request.getChiTiet()) {
            // Giảm tồn kho trong drug-service
            boolean stockDecreased = drugServiceClient.decreaseStock(ct.getMaThuoc(), ct.getSoLuong(), token);
            if (!stockDecreased) {
                log.warn("Không thể giảm tồn kho cho thuốc: {}", ct.getMaThuoc());
                // Có thể throw exception hoặc rollback transaction ở đây
                throw new ValidationException("Không thể giảm tồn kho cho thuốc: " + ct.getMaThuoc());
            }
            
            // Tạo lịch sử tồn trong inventory-service
            String lyDo = "Bán hàng từ hóa đơn " + request.getMaHoaDon();
            boolean historyCreated = inventoryServiceClient.createInventoryHistory(
                ct.getMaThuoc(), 
                ct.getSoLuong(), 
                lyDo, 
                token
            );
            if (!historyCreated) {
                log.warn("Không thể tạo lịch sử tồn kho cho thuốc: {}", ct.getMaThuoc());
            }
        }

        log.info("Tạo hóa đơn thành công: {}", request.getMaHoaDon());

        // Trả về response
        HoaDonResponse response = new HoaDonResponse();
        response.setMaHoaDon(hoaDon.getMaHoaDon());
        response.setNgayBan(hoaDon.getNgayBan());
        response.setMaNV(hoaDon.getMaNV());
        response.setTongTien(hoaDon.getTongTien());
        response.setChiTiet(request.getChiTiet());

        return response;
    }

    @Transactional(readOnly = true)
    public HoaDonResponse getHoaDon(String maHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hóa đơn với mã: " + maHoaDon));

        HoaDonResponse response = new HoaDonResponse();
        response.setMaHoaDon(hoaDon.getMaHoaDon());
        response.setNgayBan(hoaDon.getNgayBan());
        response.setMaNV(hoaDon.getMaNV());
        response.setTongTien(hoaDon.getTongTien());

        // Convert chi tiết
        List<ChiTietRequest> chiTiet = hoaDon.getChiTiet().stream()
                .map(ct -> new ChiTietRequest(ct.getMaThuoc(), ct.getSoLuong(), ct.getDonGia()))
                .collect(Collectors.toList());
        response.setChiTiet(chiTiet);

        return response;
    }

    @Transactional(readOnly = true)
    public List<HoaDonResponse> getAllHoaDon() {
        return hoaDonRepository.findAll().stream()
                .map(hoaDon -> {
                    HoaDonResponse response = new HoaDonResponse();
                    response.setMaHoaDon(hoaDon.getMaHoaDon());
                    response.setNgayBan(hoaDon.getNgayBan());
                    response.setMaNV(hoaDon.getMaNV());
                    response.setTongTien(hoaDon.getTongTien());

                    List<ChiTietRequest> chiTiet = hoaDon.getChiTiet().stream()
                            .map(ct -> new ChiTietRequest(ct.getMaThuoc(), ct.getSoLuong(), ct.getDonGia()))
                            .collect(Collectors.toList());
                    response.setChiTiet(chiTiet);

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteHoaDon(String maHoaDon) {
        log.info("Đang xóa hóa đơn: {}", maHoaDon);

        if (!hoaDonRepository.existsById(maHoaDon)) {
            throw new ResourceNotFoundException("Không tìm thấy hóa đơn với mã: " + maHoaDon);
        }

        hoaDonRepository.deleteById(maHoaDon);
    }
}

