package com.pma.supplier.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DrugServiceClient {
    private final RestTemplate restTemplate;
    private static final String DRUG_SERVICE_NAME = "http://drug-service";

    /**
     * Gọi drug-service để cập nhật tồn kho thuốc
     */
    public boolean updateStock(String maThuoc, Integer soLuong, String token) {
        try {
            String url = DRUG_SERVICE_NAME + "/api/drugs/" + maThuoc + "/stock";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            Map<String, Object> body = new HashMap<>();
            body.put("maThuoc", maThuoc);
            body.put("soLuong", soLuong);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            log.debug("Gọi drug-service để cập nhật tồn kho thuốc {}, số lượng: {}", maThuoc, soLuong);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PATCH, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Boolean success = (Boolean) responseBody.get("success");
                log.info("Cập nhật tồn kho thuốc {} thành công", maThuoc);
                return Boolean.TRUE.equals(success);
            }

            log.warn("Không thể cập nhật tồn kho thuốc {}", maThuoc);
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi gọi drug-service để cập nhật tồn kho: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Kiểm tra mã thuốc có tồn tại trong drug-service không
     */
    public boolean checkDrugExists(String maThuoc, String token) {
        try {
            String url = DRUG_SERVICE_NAME + "/api/drugs/" + maThuoc;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            log.debug("Kiểm tra thuốc {} có tồn tại không", maThuoc);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Thuốc {} tồn tại trong hệ thống", maThuoc);
                return true;
            }

            log.warn("Thuốc {} không tồn tại", maThuoc);
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra thuốc {}: {}", maThuoc, e.getMessage());
            return false;
        }
    }
}
