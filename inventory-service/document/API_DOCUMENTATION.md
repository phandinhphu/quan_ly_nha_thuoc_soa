# INVENTORY SERVICE - API DOCUMENTATION

## BASE URL
```
http://localhost:8084/api
```

---

## 1. WAREHOUSE APIs (Quản lý Kho)

### 1.1. Tạo kho mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/warehouses` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maKho": "string",<br>&nbsp;&nbsp;"tenKho": "string",<br>&nbsp;&nbsp;"diaChi": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo kho thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maKho": "KHO001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenKho": "Kho trung tâm",<br>&nbsp;&nbsp;&nbsp;&nbsp;"diaChi": "123 Đường ABC, TP.HCM",<br>&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Kho chính lưu trữ thuốc"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã kho đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Yêu cầu quyền ADMIN hoặc MANAGER |

---

### 1.2. Lấy danh sách tất cả kho

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/warehouses` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả kho |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.3. Lấy thông tin kho theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/warehouses/{maKho}` |
| **Method** | `GET` |
| **URL Params** | maKho=[string] - Mã kho |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết kho |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy kho<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.4. Cập nhật kho

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/warehouses/{maKho}` |
| **Method** | `PUT` |
| **URL Params** | maKho=[string] - Mã kho cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maKho": "string",<br>&nbsp;&nbsp;"tenKho": "string",<br>&nbsp;&nbsp;"diaChi": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin kho đã cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy kho<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.5. Xóa kho

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/warehouses/{maKho}` |
| **Method** | `DELETE` |
| **URL Params** | maKho=[string] - Mã kho cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa kho thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy kho<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## 2. STOCK ALERT APIs (Quản lý Cảnh Báo Tồn Kho)

### 2.1. Tạo cảnh báo tồn kho mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;"nguongToiThieu": number,<br>&nbsp;&nbsp;"soLuongHienTai": number,<br>&nbsp;&nbsp;"trangThai": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo cảnh báo tồn thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenThuoc": "Paracetamol",<br>&nbsp;&nbsp;&nbsp;&nbsp;"nguongToiThieu": 10,<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongHienTai": 5,<br>&nbsp;&nbsp;&nbsp;&nbsp;"trangThai": "THAP"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Cảnh báo cho thuốc này đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Trạng thái có thể là: THAP (Tồn thấp), BINH_THUONG (Bình thường) |

---

### 2.2. Lấy danh sách tất cả cảnh báo tồn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả cảnh báo tồn |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.3. Lấy thông tin cảnh báo theo mã thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/{maThuoc}` |
| **Method** | `GET` |
| **URL Params** | maThuoc=[string] - Mã thuốc |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin cảnh báo tồn của thuốc |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy cảnh báo<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.4. Lấy danh sách thuốc tồn thấp

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/low-stock` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách các thuốc có số lượng hiện tại < ngưỡng tối thiểu |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ trả về các cảnh báo có trạng thái THAP |

---

### 2.5. Lấy cảnh báo theo trạng thái

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/status/{trangThai}` |
| **Method** | `GET` |
| **URL Params** | trangThai=[string] - Trạng thái (THAP hoặc BINH_THUONG) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách cảnh báo theo trạng thái |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.6. Cập nhật cảnh báo tồn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/{maThuoc}` |
| **Method** | `PUT` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;"nguongToiThieu": number,<br>&nbsp;&nbsp;"soLuongHienTai": number,<br>&nbsp;&nbsp;"trangThai": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin cảnh báo đã cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy cảnh báo<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.7. Cập nhật số lượng hiện tại

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/{maThuoc}/current-stock` |
| **Method** | `PATCH` |
| **URL Params** | maThuoc=[string] - Mã thuốc<br>soLuongHienTai=[number] - Số lượng hiện tại mới |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER, USER |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin cảnh báo với số lượng đã cập nhật |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy cảnh báo<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Tự động cập nhật trạng thái dựa trên số lượng hiện tại và ngưỡng tối thiểu |

---

### 2.8. Xóa cảnh báo tồn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/stock-alerts/{maThuoc}` |
| **Method** | `DELETE` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần xóa cảnh báo |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa cảnh báo tồn thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy cảnh báo<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## 3. INVENTORY HISTORY APIs (Lịch Sử Tồn Kho)

### 3.1. Tạo lịch sử tồn kho

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER, USER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;"loaiGiaoDich": "string",<br>&nbsp;&nbsp;"soLuongThayDoi": number,<br>&nbsp;&nbsp;"soLuongTruoc": number,<br>&nbsp;&nbsp;"soLuongSau": number,<br>&nbsp;&nbsp;"lyDo": "string",<br>&nbsp;&nbsp;"nguoiThucHien": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo lịch sử tồn thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maLS": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenThuoc": "Paracetamol",<br>&nbsp;&nbsp;&nbsp;&nbsp;"loaiGiaoDich": "NHAP",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongThayDoi": 100,<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongTruoc": 50,<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongSau": 150,<br>&nbsp;&nbsp;&nbsp;&nbsp;"thoiGian": "2025-12-27T10:30:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;"lyDo": "Nhập hàng từ NCC",<br>&nbsp;&nbsp;&nbsp;&nbsp;"nguoiThucHien": "admin"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Loại giao dịch: NHAP (Nhập kho), XUAT (Xuất kho), DIEU_CHINH (Điều chỉnh) |

---

### 3.2. Lấy danh sách tất cả lịch sử tồn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả lịch sử tồn kho |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.3. Lấy lịch sử theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history/{maLS}` |
| **Method** | `GET` |
| **URL Params** | maLS=[number] - Mã lịch sử |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết lịch sử tồn |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy lịch sử<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.4. Lấy lịch sử theo mã thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history/drug/{maThuoc}` |
| **Method** | `GET` |
| **URL Params** | maThuoc=[string] - Mã thuốc |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách lịch sử tồn của thuốc |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.5. Lấy lịch sử theo khoảng thời gian

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history/date-range` |
| **Method** | `GET` |
| **URL Params** | start=[datetime] - Thời gian bắt đầu (ISO format)<br>end=[datetime] - Thời gian kết thúc (ISO format) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách lịch sử tồn trong khoảng thời gian |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | Format datetime: yyyy-MM-ddTHH:mm:ss (VD: 2025-12-01T00:00:00) |

---

### 3.6. Lấy 10 lịch sử gần nhất

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history/recent` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** 10 lịch sử tồn gần nhất |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | Sắp xếp theo thời gian giảm dần |

---

### 3.7. Xóa lịch sử tồn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8084/api/inventory-history/{maLS}` |
| **Method** | `DELETE` |
| **URL Params** | maLS=[number] - Mã lịch sử cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa lịch sử tồn thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy lịch sử<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## THÔNG TIN BỔ SUNG

### Authentication
Tất cả các API đều yêu cầu JWT token trong header:
```
Authorization: Bearer {token}
```

### Authorization Roles
- **ADMIN**: Toàn quyền (tạo, sửa, xóa tất cả)
- **MANAGER**: Quản lý (tạo, sửa kho, cảnh báo tồn, lịch sử)
- **STAFF**: Nhân viên (xem thông tin)

### Common Error Codes
- **400 BAD REQUEST**: Dữ liệu không hợp lệ (validation error)
- **401 UNAUTHORIZED**: Chưa đăng nhập hoặc token không hợp lệ
- **403 FORBIDDEN**: Không có quyền truy cập
- **404 NOT FOUND**: Không tìm thấy tài nguyên
- **409 CONFLICT**: Dữ liệu bị trùng lặp
- **500 INTERNAL SERVER ERROR**: Lỗi server

### Response Format
Tất cả response đều có format chuẩn:
```json
{
  "success": true/false,
  "message": "Thông báo",
  "data": {...}
}
```

### Transaction Types
- **NHAP**: Nhập kho (soLuongThayDoi > 0)
- **XUAT**: Xuất kho (soLuongThayDoi < 0)
- **DIEU_CHINH**: Điều chỉnh tồn kho

### Alert Status
- **THAP**: Tồn kho thấp (soLuongHienTai < nguongToiThieu)
- **BINH_THUONG**: Tồn kho bình thường (soLuongHienTai >= nguongToiThieu)

### Date/Time Format
- Date: `yyyy-MM-dd` (VD: `2025-12-31`)
- DateTime: `yyyy-MM-ddTHH:mm:ss` (VD: `2025-12-27T10:30:00`)
- Timezone: UTC
