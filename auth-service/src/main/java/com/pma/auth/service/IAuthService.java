package com.pma.auth.service;

import com.pma.auth.dto.LoginRequest;
import com.pma.auth.dto.RegisterRequest;
import com.pma.auth.dto.UserDetailsResponse;
import com.pma.auth.entity.NhanVien;

public interface IAuthService {
	String register(RegisterRequest request);
	String login(LoginRequest request);
	UserDetailsResponse verifyToken(String token);
	NhanVien getNhanVienByMaNV(String maNV);
}
