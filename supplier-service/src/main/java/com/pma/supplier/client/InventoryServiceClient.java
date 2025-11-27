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
public class InventoryServiceClient {
    private final RestTemplate restTemplate;
    private static final String INVENTORY_SERVICE_NAME = "http://inventory-service";

    /**
     * Gọi inventory-service để tạo lịch sử tồn kho
     */
    public boolean createInventoryHistory(String maThuoc, Integer soLuongThayDoi, String lyDo, String token) {
        try {
            String url = INVENTORY_SERVICE_NAME + "/api/inventory-history";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", token);

            Map<String, Object> body = new HashMap<>();
            body.put("maThuoc", maThuoc);
            body.put("soLuongThayDoi", soLuongThayDoi);
            body.put("lyDo", lyDo);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            log.debug("Gọi inventory-service để tạo lịch sử tồn kho thuốc {}, số lượng: {}", maThuoc, soLuongThayDoi);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Boolean success = (Boolean) responseBody.get("success");
                log.info("Tạo lịch sử tồn kho thuốc {} thành công", maThuoc);
                return Boolean.TRUE.equals(success);
            }

            log.warn("Không thể tạo lịch sử tồn kho thuốc {}", maThuoc);
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi gọi inventory-service để tạo lịch sử tồn kho: {}", e.getMessage());
            return false;
        }
    }
}
