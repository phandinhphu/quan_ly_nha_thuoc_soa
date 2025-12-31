package com.pma.drug.job;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pma.drug.client.IInventoryServiceClient;
import com.pma.drug.client.impl.AuthServiceClient;
import com.pma.drug.dto.CanhBaoTonRequest;
import com.pma.drug.entity.Thuoc;
import com.pma.drug.repository.ThuocRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryCheckJob {
	private final ThuocRepository thuocRepository;
	private final IInventoryServiceClient inventoryServiceClient;
	private final AuthServiceClient authServiceClient;

	/**
	 * Cron job chạy hàng ngày vào lúc 00:00 để kiểm tra tồn kho và cập nhật cảnh
	 * báo tồn
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkInventoryAndUpdateAlert() {
		log.info("Bắt đầu cron job kiểm tra tồn kho");

		// Lấy danh sách tất cả thuốc
		List<Thuoc> danhSachThuoc = thuocRepository.findAll();

		for (Thuoc thuoc : danhSachThuoc) {
			int currentStock = thuoc.getSoLuongTon();
			int minStock = 10;

			// Gọi AuthService để lấy token admin
			String adminToken = authServiceClient.loginAsAdmin();

			/**
			 * Gọi InventoryService để cập nhật cảnh báo tồn Nếu chưa có cảnh báo tồn thì
			 * tạo mới, nếu đã có thì cập nhật
			 */
			try {
				// Kiểm tra tồn tại cảnh báo tồn nếu không tồn tại sẽ ném ngoại lệ FeignException.NotFound
				inventoryServiceClient.getCanhBaoTon(thuoc.getMaThuoc(), "Bearer " + adminToken);
				
				// Cập nhật cảnh báo tồn
				CanhBaoTonRequest updateRequest = new CanhBaoTonRequest();
				updateRequest.setMaThuoc(thuoc.getMaThuoc());
				updateRequest.setSoLuongHienTai(currentStock);
				updateRequest.setSoLuongToiThieu(minStock);
				updateRequest.setTrangThai(""); // Inventory service sẽ tự xác định trạng thái

				ResponseEntity<Map<String, Object>> updateResponse = inventoryServiceClient
						.updateCanhBaoTon(thuoc.getMaThuoc(), updateRequest, "Bearer " + adminToken);

				if (updateResponse.getStatusCode() == HttpStatus.OK && updateResponse.getBody() != null) {
					log.info("Cập nhật cảnh báo tồn cho thuốc {} thành công", thuoc.getMaThuoc());
				} else {
					log.warn("Không thể cập nhật cảnh báo tồn cho thuốc {}", thuoc.getMaThuoc());
				}

			} catch (FeignException.NotFound e) {
				log.info("Cảnh báo tồn cho thuốc {} chưa tồn tại, tiến hành tạo mới", thuoc.getMaThuoc());

				// Tạo mới cảnh báo tồn
				CanhBaoTonRequest createRequest = new CanhBaoTonRequest();
				createRequest.setMaThuoc(thuoc.getMaThuoc());
				createRequest.setSoLuongHienTai(currentStock);
				createRequest.setSoLuongToiThieu(minStock);
				createRequest.setTrangThai(""); // Inventory service sẽ tự xác định trạng thái

				ResponseEntity<Map<String, Object>> createResponse = inventoryServiceClient
						.createCanhBaoTon(createRequest, "Bearer " + adminToken);

				if (createResponse.getStatusCode() == HttpStatus.CREATED && createResponse.getBody() != null) {
					log.info("Tạo cảnh báo tồn cho thuốc {} thành công", thuoc.getMaThuoc());
				} else {
					log.warn("Không thể tạo cảnh báo tồn cho thuốc {}", thuoc.getMaThuoc());
				}
			} catch (Exception e) {
				log.error("Lỗi khi cập nhật cảnh báo tồn cho thuốc {}: {}", thuoc.getMaThuoc(), e.getMessage());
			}
		}

		log.info("Kết thúc cron job kiểm tra tồn kho");
	}
}
