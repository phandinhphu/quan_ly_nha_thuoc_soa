package com.pma.inventory.service.impl;

import com.pma.inventory.dto.LichSuTonRequest;
import com.pma.inventory.dto.LichSuTonResponse;
import com.pma.inventory.entity.LichSuTon;
import com.pma.inventory.exception.ResourceNotFoundException;
import com.pma.inventory.repository.LichSuTonRepository;
import com.pma.inventory.service.ILichSuTonService;

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
public class LichSuTonService implements ILichSuTonService {
    
    private final LichSuTonRepository lichSuTonRepository;
    
    @Override
    @Transactional
    public LichSuTonResponse createLichSuTon(LichSuTonRequest request) {
        LichSuTon lichSu = new LichSuTon();
        lichSu.setMaThuoc(request.getMaThuoc());
        lichSu.setNgayCapNhat(LocalDateTime.now());
        lichSu.setSoLuongThayDoi(request.getSoLuongThayDoi());
        lichSu.setLyDo(request.getLyDo());
        
        LichSuTon saved = lichSuTonRepository.save(lichSu);
        log.info("Đã tạo lịch sử tồn cho thuốc: {}", saved.getMaThuoc());
        
        return toResponse(saved);
    }

    @Override
    public List<LichSuTonResponse> getAllLichSuTon() {
        return lichSuTonRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LichSuTonResponse getLichSuTon(Integer maLS) {
        LichSuTon lichSu = lichSuTonRepository.findById(maLS)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch sử: " + maLS));
        return toResponse(lichSu);
    }

    @Override
    public List<LichSuTonResponse> getLichSuByMaThuoc(String maThuoc) {
        return lichSuTonRepository.findByMaThuocOrderByNgayCapNhatDesc(maThuoc).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LichSuTonResponse> getLichSuByDateRange(LocalDateTime start, LocalDateTime end) {
        return lichSuTonRepository.findByNgayCapNhatBetweenOrderByNgayCapNhatDesc(start, end).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LichSuTonResponse> getRecentLichSuTon() {
        return lichSuTonRepository.findTop10ByOrderByNgayCapNhatDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteLichSuTon(Integer maLS) {
        if (!lichSuTonRepository.existsById(maLS)) {
            throw new ResourceNotFoundException("Không tìm thấy lịch sử: " + maLS);
        }
        
        lichSuTonRepository.deleteById(maLS);
        log.info("Đã xóa lịch sử: {}", maLS);
    }
    
    private LichSuTonResponse toResponse(LichSuTon lichSu) {
        LichSuTonResponse response = new LichSuTonResponse();
        response.setMaLS(lichSu.getMaLS());
        response.setMaThuoc(lichSu.getMaThuoc());
        response.setNgayCapNhat(lichSu.getNgayCapNhat());
        response.setSoLuongThayDoi(lichSu.getSoLuongThayDoi());
        response.setLyDo(lichSu.getLyDo());
        return response;
    }
}
