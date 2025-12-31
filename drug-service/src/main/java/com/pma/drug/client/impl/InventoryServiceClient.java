package com.pma.drug.client.impl;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pma.drug.client.IInventoryServiceClient;
import com.pma.drug.dto.CanhBaoTonRequest;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceClient {
	private final IInventoryServiceClient inventoryServiceClient;

	/**
	 * Gọi inventory-service để tạo cảnh báo tồn nếu chưa có thì tạo mới, nếu đã có
	 * thì cập nhật số lượng hiện tại
	 */
	public boolean updateSoLuongHienTai(String maThuoc, Integer soLuongHienTai, String token) {
		try {
			log.debug("Gọi inventory-service để tạo cảnh báo tồn cho thuốc {}", maThuoc);
 
			// Kiểm trả tồn tại nếu không tồn tại sẽ ném ngoại lệ FeignException.NotFound
			inventoryServiceClient.getCanhBaoTon(maThuoc, "Bearer " + token);

			log.info("Cảnh báo tồn cho thuốc {} đã tồn tại, tiến hành cập nhật số lượng hiện tại {}", maThuoc, soLuongHienTai);

			ResponseEntity<Map<String, Object>> updateResponse = inventoryServiceClient.updateCurrentStock(maThuoc,
					soLuongHienTai, "Bearer " + token);

			if (updateResponse.getStatusCode() == HttpStatus.OK && updateResponse.getBody() != null) {
				Map<String, Object> responseBody = updateResponse.getBody();
				Boolean success = (Boolean) responseBody.get("success");
				log.info("Cập nhật số lượng hiện tại cảnh báo tồn cho thuốc {} thành công", maThuoc);
				return Boolean.TRUE.equals(success);
			} else {
				log.warn("Không thể cập nhật số lượng hiện tại cảnh báo tồn cho thuốc {}", maThuoc);
				return false;
			}
		} catch (FeignException.NotFound e) {
			log.info("Cảnh báo tồn cho thuốc {} chưa tồn tại, tiến hành tạo mới", maThuoc);

			CanhBaoTonRequest request = new CanhBaoTonRequest();
			request.setMaThuoc(maThuoc);
			request.setSoLuongHienTai(soLuongHienTai);
			request.setSoLuongToiThieu(10); // Mặc định
			request.setTrangThai(""); // Inventory-service sẽ xử lý trạng thái

			ResponseEntity<Map<String, Object>> createResponse = inventoryServiceClient.createCanhBaoTon(request,
					"Bearer " + token);

			if (createResponse.getStatusCode() == HttpStatus.CREATED && createResponse.getBody() != null) {
				Map<String, Object> responseBody = createResponse.getBody();
				Boolean success = (Boolean) responseBody.get("success");
				log.info("Tạo cảnh báo tồn cho thuốc {} thành công", maThuoc);
				return Boolean.TRUE.equals(success);
			} else {
				log.warn("Không thể tạo cảnh báo tồn cho thuốc {}", maThuoc);
				return false;
			}
		} catch (Exception e) {
			log.error("Lỗi khi gọi inventory-service để cập nhật số lượng hiện tại cảnh báo tồn cho thuốc {}: {}",
					maThuoc, e.getMessage());
			return false;
		}
	}
}
