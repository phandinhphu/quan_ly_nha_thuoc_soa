package com.pma.inventory.client;

import com.pma.inventory.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {
	private final IAuthServiceClient authServiceClient;

	public Map<String, Object> verifyToken(String token) {
		try {
			log.debug("Gọi auth-service để verify token");

			ResponseEntity<Map<String, Object>> response = authServiceClient.verifyToken("Bearer " + token);
			
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
