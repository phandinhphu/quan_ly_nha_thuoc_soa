package com.pma.drug.client.impl;

import com.pma.drug.client.IAuthServiceClient;
import com.pma.drug.dto.LoginRequest;
import com.pma.drug.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {
	
	@Value("${admin.username}")
	private String admin_username;
	@Value("${admin.password}")
	private String admin_password;
    
	private final IAuthServiceClient authServiceClient;
	
    /**
     * Gọi đến auth-service để xác thực token qua Eureka
     */
    public UserDetailsDto verifyToken(String token) {
        try {
            
            log.debug("Gọi auth-service để verify token");
            
            ResponseEntity<Map<String, Object>> response =
            		authServiceClient.verifyToken("Bearer " + token);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                Boolean success = (Boolean) body.get("success");
                
                if (Boolean.TRUE.equals(success)) {
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    
                    UserDetailsDto userDetails = new UserDetailsDto();
                    userDetails.setTenDangNhap((String) data.get("tenDangNhap"));
                    userDetails.setMaNV((String) data.get("maNV"));
                    userDetails.setHoTen((String) data.get("hoTen"));
                    userDetails.setVaiTro((String) data.get("vaiTro"));
                    userDetails.setTrangThai((String) data.get("trangThai"));
                    
                    log.debug("Token hợp lệ cho user: {}", userDetails.getTenDangNhap());
                    return userDetails;
                }
            }
            
            log.warn("Token không hợp lệ hoặc phản hồi không đúng format");
            return null;
            
        } catch (Exception e) {
            log.error("Lỗi khi gọi auth-service: {}", e.getMessage());
            return null;
        }
    }
    
    /*
     * Gọi đến auth-service để đăng nhập và lấy token (để job có thể gọi đến các service khác)
     */
    public String loginAsAdmin() {
		try {
			
			log.debug("Gọi auth-service để đăng nhập với tài khoản admin");
			
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setTenDangNhap(admin_username);
			loginRequest.setMatKhau(admin_password);
			
			ResponseEntity<Map<String, Object>> response =
					authServiceClient.login(loginRequest);
			
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				Map<String, Object> body = response.getBody();
				Boolean success = (Boolean) body.get("success");
				
				if (Boolean.TRUE.equals(success)) {
					Map<String, Object> data = (Map<String, Object>) body.get("data");
					String token = (String) data.get("token");
					
					log.debug("Đăng nhập thành công, nhận được token");
					return token;
				}
			}
			
			log.warn("Đăng nhập thất bại hoặc phản hồi không đúng format");
			return null;
			
		} catch (Exception e) {
			log.error("Lỗi khi gọi auth-service: {}", e.getMessage());
			return null;
		}
    }
}
