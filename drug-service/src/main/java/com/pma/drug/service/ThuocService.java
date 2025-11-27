package com.pma.drug.service;

import com.pma.drug.dto.ThuocRequest;
import com.pma.drug.dto.ThuocResponse;
import com.pma.drug.entity.DonViTinh;
import com.pma.drug.entity.LoaiThuoc;
import com.pma.drug.entity.Thuoc;
import com.pma.drug.exception.ResourceNotFoundException;
import com.pma.drug.exception.ValidationException;
import com.pma.drug.repository.DonViTinhRepository;
import com.pma.drug.repository.LoaiThuocRepository;
import com.pma.drug.repository.ThuocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThuocService {
    
    private final ThuocRepository thuocRepository;
    private final LoaiThuocRepository loaiThuocRepository;
    private final DonViTinhRepository donViTinhRepository;
    
    @Transactional
    public ThuocResponse createThuoc(ThuocRequest request) {
        log.info("Đang tạo thuốc mới: {}", request.getMaThuoc());
        
        if (thuocRepository.existsByMaThuoc(request.getMaThuoc())) {
            throw new ValidationException("Mã thuốc đã tồn tại");
        }
        
        // Validate loại thuốc và đơn vị tính
        LoaiThuoc loaiThuoc = loaiThuocRepository.findById(request.getMaLoai())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + request.getMaLoai()));
        
        DonViTinh donViTinh = donViTinhRepository.findById(request.getMaDonVi())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vị tính với mã: " + request.getMaDonVi()));
        
        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(request.getMaThuoc());
        thuoc.setTenThuoc(request.getTenThuoc());
        thuoc.setLoaiThuoc(loaiThuoc);
        thuoc.setDonViTinh(donViTinh);
        thuoc.setGiaNhap(request.getGiaNhap());
        thuoc.setGiaBan(request.getGiaBan());
        thuoc.setHanSuDung(request.getHanSuDung());
        thuoc.setNhaSanXuat(request.getNhaSanXuat());
        thuoc.setSoLuongTon(request.getSoLuongTon() != null ? request.getSoLuongTon() : 0);
        thuoc.setMoTa(request.getMoTa());
        
        thuoc = thuocRepository.save(thuoc);
        
        return convertToResponse(thuoc);
    }
    
    @Transactional
    public ThuocResponse updateThuoc(String maThuoc, ThuocRequest request) {
        log.info("Đang cập nhật thuốc: {}", maThuoc);
        
        Thuoc thuoc = thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + maThuoc));
        
        // Validate loại thuốc và đơn vị tính
        LoaiThuoc loaiThuoc = loaiThuocRepository.findById(request.getMaLoai())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + request.getMaLoai()));
        
        DonViTinh donViTinh = donViTinhRepository.findById(request.getMaDonVi())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vị tính với mã: " + request.getMaDonVi()));
        
        thuoc.setTenThuoc(request.getTenThuoc());
        thuoc.setLoaiThuoc(loaiThuoc);
        thuoc.setDonViTinh(donViTinh);
        thuoc.setGiaNhap(request.getGiaNhap());
        thuoc.setGiaBan(request.getGiaBan());
        thuoc.setHanSuDung(request.getHanSuDung());
        thuoc.setNhaSanXuat(request.getNhaSanXuat());
        thuoc.setMoTa(request.getMoTa());
        
        thuoc = thuocRepository.save(thuoc);
        
        return convertToResponse(thuoc);
    }
    
    @Transactional
    public ThuocResponse updateStock(String maThuoc, Integer quantity) {
        log.info("Đang cập nhật tồn kho cho thuốc: {}, số lượng: {}", maThuoc, quantity);
        
        Thuoc thuoc = thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + maThuoc));
        
        thuoc.setSoLuongTon(thuoc.getSoLuongTon() + quantity);
        
        if (thuoc.getSoLuongTon() < 0) {
            throw new ValidationException("Số lượng tồn kho không được âm");
        }
        
        thuoc = thuocRepository.save(thuoc);
        
        return convertToResponse(thuoc);
    }
    
    @Transactional(readOnly = true)
    public ThuocResponse getThuoc(String maThuoc) {
        Thuoc thuoc = thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + maThuoc));
        
        return convertToResponse(thuoc);
    }
    
    @Transactional(readOnly = true)
    public List<ThuocResponse> getAllThuoc() {
        return thuocRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ThuocResponse> searchThuocByName(String tenThuoc) {
        return thuocRepository.findByTenThuocContaining(tenThuoc).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ThuocResponse> getThuocByLoai(String maLoai) {
        return thuocRepository.findByLoaiThuoc_MaLoai(maLoai).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ThuocResponse> getLowStockDrugs(Integer threshold) {
        return thuocRepository.findLowStockDrugs(threshold).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ThuocResponse> getExpiringDrugs(Integer daysAhead) {
        LocalDate checkDate = LocalDate.now().plusDays(daysAhead);
        return thuocRepository.findExpiringDrugs(checkDate).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteThuoc(String maThuoc) {
        log.info("Đang xóa thuốc: {}", maThuoc);
        
        if (!thuocRepository.existsById(maThuoc)) {
            throw new ResourceNotFoundException("Không tìm thấy thuốc với mã: " + maThuoc);
        }
        
        thuocRepository.deleteById(maThuoc);
    }
    
    private ThuocResponse convertToResponse(Thuoc thuoc) {
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
