package com.pma.drug.service.impl;

import com.pma.drug.dto.LoaiThuocRequest;
import com.pma.drug.entity.LoaiThuoc;
import com.pma.drug.exception.ResourceNotFoundException;
import com.pma.drug.exception.ValidationException;
import com.pma.drug.repository.LoaiThuocRepository;
import com.pma.drug.service.ILoaiThuocService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoaiThuocService implements ILoaiThuocService {
    
    private final LoaiThuocRepository loaiThuocRepository;
    
    @Override
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

    @Override
    @Transactional
    public LoaiThuoc updateLoaiThuoc(String maLoai, LoaiThuocRequest request) {
        log.info("Đang cập nhật loại thuốc: {}", maLoai);
        
        LoaiThuoc loaiThuoc = loaiThuocRepository.findById(maLoai)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai));
        
        loaiThuoc.setTenLoai(request.getTenLoai());
        loaiThuoc.setMoTa(request.getMoTa());
        
        return loaiThuocRepository.save(loaiThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public LoaiThuoc getLoaiThuoc(String maLoai) {
        return loaiThuocRepository.findById(maLoai)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoaiThuoc> getAllLoaiThuoc() {
        return loaiThuocRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoaiThuoc> searchLoaiThuocByName(String tenLoai, int page, int size) {
		log.info("Đang tìm kiếm loại thuốc với tên chứa: {}", tenLoai);
		Pageable pageable = PageRequest.of(page, size);
		
		if (tenLoai == null || tenLoai.isEmpty()) {
			return loaiThuocRepository.findAll(pageable);
		}
		
		return loaiThuocRepository.findByTenLoaiContainingIgnoreCase(tenLoai, pageable);
	}

    @Override
    @Transactional
    public void deleteLoaiThuoc(String maLoai) {
        log.info("Đang xóa loại thuốc: {}", maLoai);
        
        if (!loaiThuocRepository.existsById(maLoai)) {
            throw new ResourceNotFoundException("Không tìm thấy loại thuốc với mã: " + maLoai);
        }
        
        loaiThuocRepository.deleteById(maLoai);
    }
}
