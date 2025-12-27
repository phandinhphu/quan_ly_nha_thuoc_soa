# SUPPLIER SERVICE - API DOCUMENTATION

## BASE URL
```
http://localhost:8080/api/suppliers
```

---

## 1. SUPPLIER APIs (Quản lý Nhà Cung Cấp)

### 1.1. Tạo nhà cung cấp mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maNCC": "string",<br>&nbsp;&nbsp;"tenNCC": "string",<br>&nbsp;&nbsp;"diaChi": "string",<br>&nbsp;&nbsp;"soDienThoai": "string",<br>&nbsp;&nbsp;"email": "string",<br>&nbsp;&nbsp;"nguoiLienHe": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo nhà cung cấp thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maNCC": "NCC001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenNCC": "Công ty TNHH Dược phẩm ABC",<br>&nbsp;&nbsp;&nbsp;&nbsp;"diaChi": "123 Đường ABC, TP.HCM",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soDienThoai": "0901234567",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "abc@example.com",<br>&nbsp;&nbsp;&nbsp;&nbsp;"nguoiLienHe": "Nguyễn Văn A"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã NCC đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Yêu cầu quyền ADMIN hoặc MANAGER |

---

### 1.2. Lấy danh sách tất cả nhà cung cấp

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả nhà cung cấp |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.3. Lấy danh sách nhà cung cấp (Phân trang)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/page` |
| **Method** | `GET` |
| **URL Params** | page=[number] - Số trang (default: 0)<br>size=[number] - Số phần tử mỗi trang (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách nhà cung cấp có phân trang |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.4. Lấy thông tin nhà cung cấp theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/{maNCC}` |
| **Method** | `GET` |
| **URL Params** | maNCC=[string] - Mã nhà cung cấp |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết nhà cung cấp |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy nhà cung cấp<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.5. Cập nhật nhà cung cấp

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/{maNCC}` |
| **Method** | `PUT` |
| **URL Params** | maNCC=[string] - Mã nhà cung cấp cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maNCC": "string",<br>&nbsp;&nbsp;"tenNCC": "string",<br>&nbsp;&nbsp;"diaChi": "string",<br>&nbsp;&nbsp;"soDienThoai": "string",<br>&nbsp;&nbsp;"email": "string",<br>&nbsp;&nbsp;"nguoiLienHe": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin nhà cung cấp đã cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy nhà cung cấp<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.6. Xóa nhà cung cấp

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/{maNCC}` |
| **Method** | `DELETE` |
| **URL Params** | maNCC=[string] - Mã nhà cung cấp cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa nhà cung cấp thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy nhà cung cấp<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## 2. RECEIPT APIs (Quản lý Phiếu Nhập)

### 2.1. Tạo phiếu nhập mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/receipts` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maPhieuNhap": "string",<br>&nbsp;&nbsp;"maNCC": "string",<br>&nbsp;&nbsp;"ngayNhap": "yyyy-MM-dd",<br>&nbsp;&nbsp;"tongTien": number,<br>&nbsp;&nbsp;"ghiChu": "string",<br>&nbsp;&nbsp;"chiTietPhieuNhap": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"soLuong": number,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"donGia": number,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"thanhTien": number<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;]<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo phiếu nhập thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maPhieuNhap": "PN001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"maNCC": "NCC001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenNCC": "Công ty ABC",<br>&nbsp;&nbsp;&nbsp;&nbsp;"ngayNhap": "2025-12-27",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tongTien": 10000000,<br>&nbsp;&nbsp;&nbsp;&nbsp;"ghiChu": "Nhập hàng tháng 12",<br>&nbsp;&nbsp;&nbsp;&nbsp;"chiTietPhieuNhap": [...]<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã phiếu nhập đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Token cần thiết để xác thực user và cập nhật số lượng thuốc |

---

### 2.2. Lấy danh sách tất cả phiếu nhập

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/receipts` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả phiếu nhập |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.3. Lấy danh sách phiếu nhập (Phân trang)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/receipts/page` |
| **Method** | `GET` |
| **URL Params** | page=[number] - Số trang (default: 0)<br>size=[number] - Số phần tử mỗi trang (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách phiếu nhập có phân trang |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.4. Lấy thông tin phiếu nhập theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/receipts/{maPhieuNhap}` |
| **Method** | `GET` |
| **URL Params** | maPhieuNhap=[string] - Mã phiếu nhập |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết phiếu nhập và chi tiết các mặt hàng |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy phiếu nhập<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.5. Xóa phiếu nhập

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/suppliers/receipts/{maPhieuNhap}` |
| **Method** | `DELETE` |
| **URL Params** | maPhieuNhap=[string] - Mã phiếu nhập cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa phiếu nhập thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy phiếu nhập<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
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
- **MANAGER**: Quản lý (tạo, sửa nhà cung cấp và phiếu nhập)

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

### Date Format
- Format ngày tháng: `yyyy-MM-dd` (VD: `2025-12-31`)
- Timezone: UTC

### Pagination Format
Các API hỗ trợ phân trang sẽ trả về:
```json
{
  "success": true/false,
  "message": "...",
  "data": {
    "items": [...],
    "pagination": [...]
  }
}
```
