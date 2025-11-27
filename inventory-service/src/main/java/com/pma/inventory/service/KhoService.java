package com.pma.inventory.service;

import com.pma.inventory.dto.KhoRequest;
import com.pma.inventory.dto.KhoResponse;
import com.pma.inventory.entity.Kho;
import com.pma.inventory.exception.ResourceNotFoundException;
import com.pma.inventory.exception.ValidationException;
import com.pma.inventory.repository.KhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KhoService {
    
    private final KhoRepository khoRepository;
    
    @Transactional
    public KhoResponse createKho(KhoRequest request) {
        if (khoRepository.existsById(request.getMaKho())) {
            throw new ValidationException("Mã kho đã tồn tại: " + request.getMaKho());
        }
        
        Kho kho = new Kho();
        kho.setMaKho(request.getMaKho());
        kho.setTenKho(request.getTenKho());
        kho.setDiaChi(request.getDiaChi());
        kho.setMoTa(request.getMoTa());
        
        Kho saved = khoRepository.save(kho);
        log.info("Đã tạo kho: {}", saved.getMaKho());
        
        return toResponse(saved);
    }
    
    public List<KhoResponse> getAllKho() {
        return khoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public KhoResponse getKho(String maKho) {
        Kho kho = khoRepository.findById(maKho)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho: " + maKho));
        return toResponse(kho);
    }
    
    @Transactional
    public KhoResponse updateKho(String maKho, KhoRequest request) {
        Kho kho = khoRepository.findById(maKho)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho: " + maKho));
        
        kho.setTenKho(request.getTenKho());
        kho.setDiaChi(request.getDiaChi());
        kho.setMoTa(request.getMoTa());
        
        Kho updated = khoRepository.save(kho);
        log.info("Đã cập nhật kho: {}", updated.getMaKho());
        
        return toResponse(updated);
    }
    
    @Transactional
    public void deleteKho(String maKho) {
        if (!khoRepository.existsById(maKho)) {
            throw new ResourceNotFoundException("Không tìm thấy kho: " + maKho);
        }
        
        khoRepository.deleteById(maKho);
        log.info("Đã xóa kho: {}", maKho);
    }
    
    private KhoResponse toResponse(Kho kho) {
        KhoResponse response = new KhoResponse();
        response.setMaKho(kho.getMaKho());
        response.setTenKho(kho.getTenKho());
        response.setDiaChi(kho.getDiaChi());
        response.setMoTa(kho.getMoTa());
        return response;
    }
}
