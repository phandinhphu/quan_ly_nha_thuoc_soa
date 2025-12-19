package com.pma.sales.client;

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

    /**
     * Kiểm tra số lượng thuốc có đủ trong kho không
     */
    public boolean checkStockAvailable(String maThuoc, Integer soLuong, String token) {
        try {
            String url = DRUG_SERVICE_NAME + "/api/drugs/" + maThuoc;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            log.debug("Kiểm tra tồn kho thuốc {}, số lượng yêu cầu: {}", maThuoc, soLuong);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                if (Boolean.TRUE.equals(body.get("success"))) {
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    Integer soLuongTon = (Integer) data.get("soLuongTon");
                    
                    if (soLuongTon != null && soLuongTon >= soLuong) {
                        log.info("Thuốc {} có đủ số lượng trong kho (tồn: {}, yêu cầu: {})", maThuoc, soLuongTon, soLuong);
                        return true;
                    } else {
                        log.warn("Thuốc {} không đủ số lượng trong kho (tồn: {}, yêu cầu: {})", maThuoc, soLuongTon, soLuong);
                        return false;
                    }
                }
            }

            log.warn("Không thể kiểm tra tồn kho thuốc {}", maThuoc);
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra tồn kho thuốc {}: {}", maThuoc, e.getMessage());
            return false;
        }
    }

    /**
     * Gọi drug-service để giảm tồn kho thuốc (khi bán)
     * Lưu ý: UpdateStockRequest trong drug-service có validation @Min(1) không cho phép số âm.
     * Service logic có thể xử lý số âm (cộng vào tồn kho), nhưng validation sẽ reject.
     * Có thể cần cập nhật drug-service để cho phép số âm hoặc tạo endpoint riêng cho giảm tồn kho.
     */
    public boolean decreaseStock(String maThuoc, Integer soLuong, String token) {
        try {
            String url = DRUG_SERVICE_NAME + "/api/drugs/" + maThuoc + "/stock";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            Map<String, Object> body = new HashMap<>();
            body.put("maThuoc", maThuoc);
            // Gửi số âm để giảm tồn kho (service sẽ cộng vào tồn kho hiện tại)
            // Lưu ý: Validation @Min(1) có thể reject, cần cập nhật drug-service
            body.put("soLuong", -soLuong);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            log.debug("Gọi drug-service để giảm tồn kho thuốc {}, số lượng: {}", maThuoc, soLuong);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PATCH, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Boolean success = (Boolean) responseBody.get("success");
                log.info("Giảm tồn kho thuốc {} thành công", maThuoc);
                return Boolean.TRUE.equals(success);
            }

            log.warn("Không thể giảm tồn kho thuốc {}", maThuoc);
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi gọi drug-service để giảm tồn kho: {}", e.getMessage());
            return false;
        }
    }
}

