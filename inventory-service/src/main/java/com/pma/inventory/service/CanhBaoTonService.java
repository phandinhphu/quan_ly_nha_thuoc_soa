package com.pma.inventory.service;

import com.pma.inventory.dto.CanhBaoTonRequest;
import com.pma.inventory.dto.CanhBaoTonResponse;
import com.pma.inventory.entity.CanhBaoTon;
import com.pma.inventory.exception.ResourceNotFoundException;
import com.pma.inventory.exception.ValidationException;
import com.pma.inventory.repository.CanhBaoTonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CanhBaoTonService {
    
    private final CanhBaoTonRepository canhBaoTonRepository;
    
    @Transactional
    public CanhBaoTonResponse createCanhBaoTon(CanhBaoTonRequest request) {
        if (canhBaoTonRepository.existsById(request.getMaThuoc())) {
            throw new ValidationException("Cảnh báo cho thuốc đã tồn tại: " + request.getMaThuoc());
        }
        
        CanhBaoTon canhBao = new CanhBaoTon();
        canhBao.setMaThuoc(request.getMaThuoc());
        canhBao.setSoLuongToiThieu(request.getSoLuongToiThieu());
        canhBao.setSoLuongHienTai(request.getSoLuongHienTai());
        canhBao.setTrangThai(calculateStatus(request.getSoLuongHienTai(), request.getSoLuongToiThieu()));
        canhBao.setCreatedAt(LocalDateTime.now());
        
        CanhBaoTon saved = canhBaoTonRepository.save(canhBao);
        log.info("Đã tạo cảnh báo tồn cho thuốc: {}", saved.getMaThuoc());
        
        return toResponse(saved);
    }
    
    public List<CanhBaoTonResponse> getAllCanhBaoTon() {
        return canhBaoTonRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public CanhBaoTonResponse getCanhBaoTon(String maThuoc) {
        CanhBaoTon canhBao = canhBaoTonRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cảnh báo cho thuốc: " + maThuoc));
        return toResponse(canhBao);
    }
    
    public List<CanhBaoTonResponse> getLowStockAlerts() {
        return canhBaoTonRepository.findLowStockAlerts().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<CanhBaoTonResponse> getCanhBaoByTrangThai(String trangThai) {
        return canhBaoTonRepository.findByTrangThai(trangThai).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CanhBaoTonResponse updateCanhBaoTon(String maThuoc, CanhBaoTonRequest request) {
        CanhBaoTon canhBao = canhBaoTonRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cảnh báo cho thuốc: " + maThuoc));
        
        canhBao.setSoLuongToiThieu(request.getSoLuongToiThieu());
        canhBao.setSoLuongHienTai(request.getSoLuongHienTai());
        canhBao.setTrangThai(calculateStatus(request.getSoLuongHienTai(), request.getSoLuongToiThieu()));
        canhBao.setCreatedAt(LocalDateTime.now());
        
        CanhBaoTon updated = canhBaoTonRepository.save(canhBao);
        log.info("Đã cập nhật cảnh báo tồn cho thuốc: {}", updated.getMaThuoc());
        
        return toResponse(updated);
    }
    
    @Transactional
    public CanhBaoTonResponse updateSoLuongHienTai(String maThuoc, Integer soLuongHienTai) {
        CanhBaoTon canhBao = canhBaoTonRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cảnh báo cho thuốc: " + maThuoc));
        
        canhBao.setSoLuongHienTai(soLuongHienTai);
        canhBao.setTrangThai(calculateStatus(soLuongHienTai, canhBao.getSoLuongToiThieu()));
        canhBao.setCreatedAt(LocalDateTime.now());
        
        CanhBaoTon updated = canhBaoTonRepository.save(canhBao);
        log.info("Đã cập nhật số lượng hiện tại cho thuốc: {}", updated.getMaThuoc());
        
        return toResponse(updated);
    }
    
    @Transactional
    public void deleteCanhBaoTon(String maThuoc) {
        if (!canhBaoTonRepository.existsById(maThuoc)) {
            throw new ResourceNotFoundException("Không tìm thấy cảnh báo cho thuốc: " + maThuoc);
        }
        
        canhBaoTonRepository.deleteById(maThuoc);
        log.info("Đã xóa cảnh báo tồn cho thuốc: {}", maThuoc);
    }
    
    private String calculateStatus(Integer soLuongHienTai, Integer soLuongToiThieu) {
        if (soLuongHienTai == null || soLuongToiThieu == null) {
            return "CHUA_XAC_DINH";
        }
        
        if (soLuongHienTai <= 0) {
            return "HET_HANG";
        } else if (soLuongHienTai <= soLuongToiThieu) {
            return "SAP_HET";
        } else {
            return "BINH_THUONG";
        }
    }
    
    private CanhBaoTonResponse toResponse(CanhBaoTon canhBao) {
        CanhBaoTonResponse response = new CanhBaoTonResponse();
        response.setMaThuoc(canhBao.getMaThuoc());
        response.setSoLuongToiThieu(canhBao.getSoLuongToiThieu());
        response.setSoLuongHienTai(canhBao.getSoLuongHienTai());
        response.setTrangThai(canhBao.getTrangThai());
        response.setCreatedAt(canhBao.getCreatedAt());
        return response;
    }
}
