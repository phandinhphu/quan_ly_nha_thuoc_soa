package com.pma.drug.service;

import com.pma.drug.dto.DonViTinhRequest;
import com.pma.drug.entity.DonViTinh;
import com.pma.drug.exception.ResourceNotFoundException;
import com.pma.drug.exception.ValidationException;
import com.pma.drug.repository.DonViTinhRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonViTinhService {
    
    private final DonViTinhRepository donViTinhRepository;
    
    @Transactional
    public DonViTinh createDonViTinh(DonViTinhRequest request) {
        log.info("Đang tạo đơn vị tính mới: {}", request.getMaDonVi());
        
        if (donViTinhRepository.existsByMaDonVi(request.getMaDonVi())) {
            throw new ValidationException("Mã đơn vị tính đã tồn tại");
        }
        
        DonViTinh donViTinh = new DonViTinh();
        donViTinh.setMaDonVi(request.getMaDonVi());
        donViTinh.setTenDonVi(request.getTenDonVi());
        
        return donViTinhRepository.save(donViTinh);
    }
    
    @Transactional
    public DonViTinh updateDonViTinh(String maDonVi, DonViTinhRequest request) {
        log.info("Đang cập nhật đơn vị tính: {}", maDonVi);
        
        DonViTinh donViTinh = donViTinhRepository.findById(maDonVi)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vị tính với mã: " + maDonVi));
        
        donViTinh.setTenDonVi(request.getTenDonVi());
        
        return donViTinhRepository.save(donViTinh);
    }
    
    @Transactional(readOnly = true)
    public DonViTinh getDonViTinh(String maDonVi) {
        return donViTinhRepository.findById(maDonVi)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vị tính với mã: " + maDonVi));
    }
    
    @Transactional(readOnly = true)
    public List<DonViTinh> getAllDonViTinh() {
        return donViTinhRepository.findAll();
    }
    
    @Transactional
    public void deleteDonViTinh(String maDonVi) {
        log.info("Đang xóa đơn vị tính: {}", maDonVi);
        
        if (!donViTinhRepository.existsById(maDonVi)) {
            throw new ResourceNotFoundException("Không tìm thấy đơn vị tính với mã: " + maDonVi);
        }
        
        donViTinhRepository.deleteById(maDonVi);
    }
}
