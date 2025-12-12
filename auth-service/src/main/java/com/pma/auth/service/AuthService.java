package com.pma.auth.service;

import com.pma.auth.dto.LoginRequest;
import com.pma.auth.dto.RegisterRequest;
import com.pma.auth.dto.UserDetailsResponse;
import com.pma.auth.entity.NhanVien;
import com.pma.auth.entity.TaiKhoan;
import com.pma.auth.exception.UnauthorizedException;
import com.pma.auth.exception.ValidationException;
import com.pma.auth.repository.NhanVienRepository;
import com.pma.auth.repository.TaiKhoanRepository;
import com.pma.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Transactional
    public String register(RegisterRequest request) {
        log.info("Đang xử lý đăng ký cho tài khoản: {}", request.getTenDangNhap());
        
        // Kiểm tra tài khoản đã tồn tại
        if (taiKhoanRepository.existsByTenDangNhap(request.getTenDangNhap())) {
            throw new ValidationException("Tên đăng nhập đã tồn tại");
        }
        
        // Kiểm tra mã nhân viên đã tồn tại
        if (nhanVienRepository.existsById(request.getMaNV())) {
            throw new ValidationException("Mã nhân viên đã tồn tại");
        }
        
        // Tạo nhân viên mới
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(request.getMaNV());
        nhanVien.setHoTen(request.getHoTen());
        nhanVien.setGioiTinh(request.getGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());
        nhanVien.setSoDienThoai(request.getSoDienThoai());
        nhanVien.setDiaChi(request.getDiaChi());
        nhanVien.setVaiTro(request.getVaiTro() != null ? request.getVaiTro() : "USER");
        nhanVienRepository.save(nhanVien);
        
        // Tạo tài khoản mới
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(request.getTenDangNhap());
        taiKhoan.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        taiKhoan.setNhanVien(nhanVien);
        taiKhoan.setTrangThai("ACTIVE");
        taiKhoanRepository.save(taiKhoan);
        
        log.info("Đăng ký thành công cho tài khoản: {}", request.getTenDangNhap());
        
        // Tạo token cho người dùng mới
        return jwtUtil.generateToken(taiKhoan.getTenDangNhap(), 
                                     nhanVien.getMaNV(), 
                                     nhanVien.getVaiTro());
    }
    
    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        log.info("Đang xử lý đăng nhập cho tài khoản: {}", request.getTenDangNhap());
        
        // Tìm tài khoản
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(request.getTenDangNhap())
                .orElseThrow(() -> new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không đúng"));
        
        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(request.getMatKhau(), taiKhoan.getMatKhau())) {
            throw new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
        
        // Kiểm tra trạng thái tài khoản
        if (!"ACTIVE".equals(taiKhoan.getTrangThai())) {
            throw new UnauthorizedException("Tài khoản đã bị khóa hoặc không hoạt động");
        }
        
        log.info("Đăng nhập thành công cho tài khoản: {}", request.getTenDangNhap());
        
        // Tạo token
        return jwtUtil.generateToken(taiKhoan.getTenDangNhap(), 
                                     taiKhoan.getNhanVien().getMaNV(), 
                                     taiKhoan.getNhanVien().getVaiTro());
    }
    
    @Transactional(readOnly = true)
    public UserDetailsResponse verifyToken(String token) {
        log.info("Đang xác thực token");
        
        // Validate token
        if (!jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("Token không hợp lệ hoặc đã hết hạn");
        }
        
        // Extract thông tin từ token
        String tenDangNhap = jwtUtil.extractUsername(token);
        String maNV = jwtUtil.extractMaNV(token);
        String vaiTro = jwtUtil.extractVaiTro(token);
        
        // Tìm tài khoản để lấy thông tin đầy đủ
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new UnauthorizedException("Người dùng không tồn tại"));
        
        // Kiểm tra trạng thái tài khoản
        if (!"ACTIVE".equals(taiKhoan.getTrangThai())) {
            throw new UnauthorizedException("Tài khoản đã bị khóa hoặc không hoạt động");
        }
        
        log.info("Token hợp lệ cho người dùng: {}", tenDangNhap);
        
        // Trả về thông tin người dùng
        return new UserDetailsResponse(
                tenDangNhap,
                maNV,
                taiKhoan.getNhanVien().getHoTen(),
                vaiTro,
                taiKhoan.getTrangThai()
        );
    }
    
    @Transactional(readOnly = true)
    public NhanVien getNhanVienByMaNV(String maNV) {
		return nhanVienRepository.findById(maNV)
				.orElseThrow(() -> new ValidationException("Nhân viên không tồn tại"));
	}
}
