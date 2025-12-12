package com.pma.auth.config;

import com.pma.auth.entity.NhanVien;
import com.pma.auth.entity.TaiKhoan;
import com.pma.auth.repository.NhanVienRepository;
import com.pma.auth.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Kiểm tra nếu đã có data thì không insert nữa
        if (nhanVienRepository.count() > 0) {
            log.info("Database already has data. Skipping initialization.");
            return;
        }

        log.info("Initializing seed data for Auth Service...");

        // Tạo nhân viên
        NhanVien admin = new NhanVien();
        admin.setMaNV("NV001");
        admin.setHoTen("Nguyễn Văn Admin");
        admin.setGioiTinh("Nam");
        admin.setNgaySinh(LocalDate.of(1990, 1, 1));
        admin.setSoDienThoai("0901234567");
        admin.setDiaChi("123 Nguyễn Huệ, Q.1, TP.HCM");
        admin.setVaiTro("ADMIN");
        nhanVienRepository.save(admin);

        NhanVien pharmacist = new NhanVien();
        pharmacist.setMaNV("NV002");
        pharmacist.setHoTen("Trần Thị Dược Sĩ");
        pharmacist.setGioiTinh("Nữ");
        pharmacist.setNgaySinh(LocalDate.of(1995, 5, 15));
        pharmacist.setSoDienThoai("0912345678");
        pharmacist.setDiaChi("456 Lê Lợi, Q.3, TP.HCM");
        pharmacist.setVaiTro("PHARMACIST");
        nhanVienRepository.save(pharmacist);

        NhanVien staff = new NhanVien();
        staff.setMaNV("NV003");
        staff.setHoTen("Lê Văn Nhân Viên");
        staff.setGioiTinh("Nam");
        staff.setNgaySinh(LocalDate.of(1998, 8, 20));
        staff.setSoDienThoai("0923456789");
        staff.setDiaChi("789 Võ Thị Sáu, Q.10, TP.HCM");
        staff.setVaiTro("STAFF");
        nhanVienRepository.save(staff);

        // Tạo tài khoản với password đã mã hóa
        TaiKhoan adminAccount = new TaiKhoan();
        adminAccount.setTenDangNhap("admin");
        adminAccount.setMatKhau(passwordEncoder.encode("admin123"));
        adminAccount.setNhanVien(admin);
        adminAccount.setTrangThai("ACTIVE");
        taiKhoanRepository.save(adminAccount);

        TaiKhoan pharmacistAccount = new TaiKhoan();
        pharmacistAccount.setTenDangNhap("duocsi");
        pharmacistAccount.setMatKhau(passwordEncoder.encode("duocsi123"));
        pharmacistAccount.setNhanVien(pharmacist);
        pharmacistAccount.setTrangThai("ACTIVE");
        taiKhoanRepository.save(pharmacistAccount);

        TaiKhoan staffAccount = new TaiKhoan();
        staffAccount.setTenDangNhap("nhanvien");
        staffAccount.setMatKhau(passwordEncoder.encode("nhanvien123"));
        staffAccount.setNhanVien(staff);
        staffAccount.setTrangThai("ACTIVE");
        taiKhoanRepository.save(staffAccount);

        log.info("Seed data initialized successfully!");
        log.info("Created accounts:");
        log.info("  - admin/admin123 (ADMIN)");
        log.info("  - duocsi/duocsi123 (PHARMACIST)");
        log.info("  - nhanvien/nhanvien123 (STAFF)");
    }
}
