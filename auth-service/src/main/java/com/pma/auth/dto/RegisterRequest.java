package com.pma.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "Tên đăng nhập không được để trống")
	private String tenDangNhap;

	@NotBlank(message = "Mật khẩu không được để trống")
	private String matKhau;

	@NotBlank(message = "Mã nhân viên không được để trống")
	private String maNV;

	@NotBlank(message = "Họ tên không được để trống")
	private String hoTen;

	private String gioiTinh;

	private LocalDate ngaySinh;

	private String soDienThoai;

	private String diaChi;

	private String vaiTro;

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

}
