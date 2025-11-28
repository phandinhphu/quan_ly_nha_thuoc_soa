package com.pma.inventory.client;

import com.pma.inventory.exception.UnauthorizedException;
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

	// Sử dụng service name từ Eureka thay vì hard code URL
	private static final String AUTH_SERVICE_NAME = "http://auth-service";

	public Map<String, Object> verifyToken(String token) {
		try {
			String url = AUTH_SERVICE_NAME + "/api/auth/verify";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			log.debug("Gọi auth-service để verify token: {}", url);

			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				Map<String, Object> body = response.getBody();
				Boolean success = (Boolean) body.get("success");

				if (Boolean.TRUE.equals(success)) {
					log.info("Token verification successful");
					return body;
				}
			}

			throw new UnauthorizedException("Token không hợp lệ");

		} catch (Exception e) {
			log.error("Lỗi khi xác thực token: {}", e.getMessage());
			throw new UnauthorizedException("Không thể xác thực token: " + e.getMessage());
		}
	}
}
