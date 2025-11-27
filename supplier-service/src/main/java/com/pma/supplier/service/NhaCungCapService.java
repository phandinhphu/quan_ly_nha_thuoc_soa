package com.pma.supplier.service;

import com.pma.supplier.dto.NhaCungCapRequest;
import com.pma.supplier.entity.NhaCungCap;
import com.pma.supplier.exception.ResourceNotFoundException;
import com.pma.supplier.exception.ValidationException;
import com.pma.supplier.repository.NhaCungCapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NhaCungCapService {
    private final NhaCungCapRepository nhaCungCapRepository;

    @Transactional
    public NhaCungCap createNhaCungCap(NhaCungCapRequest request) {
        log.info("Đang tạo nhà cung cấp mới: {}", request.getMaNCC());

        if (nhaCungCapRepository.existsById(request.getMaNCC())) {
            throw new ValidationException("Mã nhà cung cấp đã tồn tại");
        }

        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(request.getMaNCC());
        ncc.setTenNCC(request.getTenNCC());
        ncc.setDiaChi(request.getDiaChi());
        ncc.setSoDienThoai(request.getSoDienThoai());
        ncc.setEmail(request.getEmail());
        ncc.setNguoiDaiDien(request.getNguoiDaiDien());

        return nhaCungCapRepository.save(ncc);
    }

    @Transactional(readOnly = true)
    public NhaCungCap getNhaCungCap(String maNCC) {
        return nhaCungCapRepository.findById(maNCC)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với mã: " + maNCC));
    }

    @Transactional(readOnly = true)
    public List<NhaCungCap> getAllNhaCungCap() {
        return nhaCungCapRepository.findAll();
    }

    @Transactional
    public NhaCungCap updateNhaCungCap(String maNCC, NhaCungCapRequest request) {
        log.info("Đang cập nhật nhà cung cấp: {}", maNCC);

        NhaCungCap ncc = nhaCungCapRepository.findById(maNCC)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với mã: " + maNCC));

        ncc.setTenNCC(request.getTenNCC());
        ncc.setDiaChi(request.getDiaChi());
        ncc.setSoDienThoai(request.getSoDienThoai());
        ncc.setEmail(request.getEmail());
        ncc.setNguoiDaiDien(request.getNguoiDaiDien());

        return nhaCungCapRepository.save(ncc);
    }

    @Transactional
    public void deleteNhaCungCap(String maNCC) {
        log.info("Đang xóa nhà cung cấp: {}", maNCC);

        if (!nhaCungCapRepository.existsById(maNCC)) {
            throw new ResourceNotFoundException("Không tìm thấy nhà cung cấp với mã: " + maNCC);
        }

        nhaCungCapRepository.deleteById(maNCC);
    }
}
