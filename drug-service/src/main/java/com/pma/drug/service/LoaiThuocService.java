package com.pma.drug.service;

import com.pma.drug.dto.LoaiThuocRequest;
import com.pma.drug.entity.LoaiThuoc;
import com.pma.drug.exception.ResourceNotFoundException;
import com.pma.drug.exception.ValidationException;
import com.pma.drug.repository.LoaiThuocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoaiThuocService {
    
    private final LoaiThuocRepository loaiThuocRepository;
    
    @Transactional
    public LoaiThuoc createLoaiThuoc(LoaiThuocRequest request) {
        log.info("Đang tạo loại thuốc mới: {}", request.getMaLoai());
        
        if (loaiThuocRepository.existsByMaLoai(request.getMaLoai())) {
            throw new ValidationException("Mã loại thuốc đã tồn tại");
        }
        
        LoaiThuoc loaiThuoc = new LoaiThuoc();
        loaiThuoc.setMaLoai(request.getMaLoai());
        loaiThuoc.setTenLoai(request.getTenLoai());
        loaiThuoc.setMoTa(request.getMoTa());
        
        return loaiThuocRepository.save(loaiThuoc);
    }
    
    @Transactional
    public LoaiThuoc updateLoaiThuoc(String maLoai, LoaiThuocRequest request) {
        log.info("Đang cập nhật loại thuốc: {}", maLoai);
        
        LoaiThuoc loaiThuoc = loaiThuocRepository.findById(maLoai)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai));
        
        loaiThuoc.setTenLoai(request.getTenLoai());
        loaiThuoc.setMoTa(request.getMoTa());
        
        return loaiThuocRepository.save(loaiThuoc);
    }
    
    @Transactional(readOnly = true)
    public LoaiThuoc getLoaiThuoc(String maLoai) {
        return loaiThuocRepository.findById(maLoai)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai));
    }
    
    @Transactional(readOnly = true)
    public List<LoaiThuoc> getAllLoaiThuoc() {
        return loaiThuocRepository.findAll();
    }
    
    @Transactional
    public void deleteLoaiThuoc(String maLoai) {
        log.info("Đang xóa loại thuốc: {}", maLoai);
        
        if (!loaiThuocRepository.existsById(maLoai)) {
            throw new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai);
        }
        
        loaiThuocRepository.deleteById(maLoai);
    }
}
