package com.pma.supplier.client;

import com.pma.supplier.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {
    private final RestTemplate restTemplate;
    private static final String AUTH_SERVICE_NAME = "http://auth-service";

    public UserDetailsDto verifyToken(String token) {
        try {
            String url = AUTH_SERVICE_NAME + "/api/auth/verify";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> request = new HttpEntity<>(headers);
            log.debug("Gọi auth-service để verify token: {}", url);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

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
}
