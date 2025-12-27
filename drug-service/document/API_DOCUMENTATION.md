# DRUG SERVICE - API DOCUMENTATION

## BASE URL
```
http://localhost:8080/api/drugs
```

---

## 1. DRUG APIs (Quản lý Thuốc)

### 1.1. Tạo thuốc mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;"tenThuoc": "string",<br>&nbsp;&nbsp;"maLoai": "string",<br>&nbsp;&nbsp;"maDonVi": "string",<br>&nbsp;&nbsp;"giaNhap": number,<br>&nbsp;&nbsp;"giaBan": number,<br>&nbsp;&nbsp;"hanSuDung": "yyyy-MM-dd",<br>&nbsp;&nbsp;"nhaSanXuat": "string",<br>&nbsp;&nbsp;"soLuongTon": number,<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo thuốc thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenThuoc": "Paracetamol",<br>&nbsp;&nbsp;&nbsp;&nbsp;"maLoai": "LT001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenLoai": "Thuốc hạ sốt",<br>&nbsp;&nbsp;&nbsp;&nbsp;"maDonVi": "DV001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDonVi": "Viên",<br>&nbsp;&nbsp;&nbsp;&nbsp;"giaNhap": 5000,<br>&nbsp;&nbsp;&nbsp;&nbsp;"giaBan": 8000,<br>&nbsp;&nbsp;&nbsp;&nbsp;"hanSuDung": "2025-12-31",<br>&nbsp;&nbsp;&nbsp;&nbsp;"nhaSanXuat": "Traphaco",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongTon": 100,<br>&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Thuốc giảm đau, hạ sốt"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã thuốc đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Yêu cầu quyền ADMIN hoặc MANAGER để tạo thuốc mới |

---

### 1.2. Lấy danh sách tất cả thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Lấy danh sách thuốc thành công",<br>&nbsp;&nbsp;"data": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"tenThuoc": "Paracetamol",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"maLoai": "LT001",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"tenLoai": "Thuốc hạ sốt",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"maDonVi": "DV001",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"tenDonVi": "Viên",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"giaNhap": 5000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"giaBan": 8000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"hanSuDung": "2025-12-31",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"nhaSanXuat": "Traphaco",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"soLuongTon": 100,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Thuốc giảm đau, hạ sốt"<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;]<br>} |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.3. Lấy thông tin thuốc theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/{maThuoc}` |
| **Method** | `GET` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần lấy thông tin |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Lấy thông tin thuốc thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenThuoc": "Paracetamol",<br>&nbsp;&nbsp;&nbsp;&nbsp;"maLoai": "LT001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenLoai": "Thuốc hạ sốt",<br>&nbsp;&nbsp;&nbsp;&nbsp;"maDonVi": "DV001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDonVi": "Viên",<br>&nbsp;&nbsp;&nbsp;&nbsp;"giaNhap": 5000,<br>&nbsp;&nbsp;&nbsp;&nbsp;"giaBan": 8000,<br>&nbsp;&nbsp;&nbsp;&nbsp;"hanSuDung": "2025-12-31",<br>&nbsp;&nbsp;&nbsp;&nbsp;"nhaSanXuat": "Traphaco",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soLuongTon": 100,<br>&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Thuốc giảm đau, hạ sốt"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.4. Tìm kiếm thuốc theo tên (Phân trang)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/search` |
| **Method** | `GET` |
| **URL Params** | tenThuoc=[string] - Tên thuốc cần tìm (optional)<br>page=[number] - Số trang (default: 0)<br>size=[number] - Số phần tử mỗi trang (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Lấy danh sách thuốc thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"content": [{...}],<br>&nbsp;&nbsp;&nbsp;&nbsp;"pageNumber": 0,<br>&nbsp;&nbsp;&nbsp;&nbsp;"pageSize": 10,<br>&nbsp;&nbsp;&nbsp;&nbsp;"totalElements": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"first": true,<br>&nbsp;&nbsp;&nbsp;&nbsp;"last": true<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | Nếu không truyền tenThuoc, sẽ lấy tất cả thuốc có phân trang |

---

### 1.5. Lấy thuốc theo loại

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/category/{maLoai}` |
| **Method** | `GET` |
| **URL Params** | maLoai=[string] - Mã loại thuốc |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Trả về danh sách thuốc theo loại |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy loại thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 1.6. Lấy danh sách thuốc sắp hết hàng

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/low-stock` |
| **Method** | `GET` |
| **URL Params** | threshold=[number] - Ngưỡng tồn kho (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách thuốc có số lượng tồn <= threshold |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ lấy các thuốc có số lượng tồn <= threshold |

---

### 1.7. Lấy danh sách thuốc sắp hết hạn

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/expiring` |
| **Method** | `GET` |
| **URL Params** | daysAhead=[number] - Số ngày trước khi hết hạn (default: 30) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách thuốc sắp hết hạn trong vòng daysAhead ngày |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Lấy các thuốc có hạn sử dụng trong vòng daysAhead ngày tới |

---

### 1.8. Cập nhật thông tin thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/{maThuoc}` |
| **Method** | `PUT` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "string",<br>&nbsp;&nbsp;"tenThuoc": "string",<br>&nbsp;&nbsp;"maLoai": "string",<br>&nbsp;&nbsp;"maDonVi": "string",<br>&nbsp;&nbsp;"giaNhap": number,<br>&nbsp;&nbsp;"giaBan": number,<br>&nbsp;&nbsp;"hanSuDung": "yyyy-MM-dd",<br>&nbsp;&nbsp;"nhaSanXuat": "string",<br>&nbsp;&nbsp;"soLuongTon": number,<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin thuốc đã được cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Cập nhật toàn bộ thông tin thuốc |

---

### 1.9. Cập nhật số lượng tồn kho

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/{maThuoc}/stock` |
| **Method** | `PATCH` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần cập nhật tồn kho |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER, USER, STAFF |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maThuoc": "TH001",<br>&nbsp;&nbsp;"soLuong": number<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin thuốc với số lượng tồn đã được cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | soLuong có thể là số dương (nhập thêm) hoặc số âm (xuất kho) |

---

### 1.10. Xóa thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/{maThuoc}` |
| **Method** | `DELETE` |
| **URL Params** | maThuoc=[string] - Mã thuốc cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa thuốc thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa thuốc |

---

## 2. CATEGORY APIs (Quản lý Loại Thuốc)

### 2.1. Tạo loại thuốc mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maLoai": "string",<br>&nbsp;&nbsp;"tenLoai": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo loại thuốc thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maLoai": "LT001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenLoai": "Thuốc hạ sốt",<br>&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Các loại thuốc giảm đau hạ sốt"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã loại đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Yêu cầu quyền ADMIN hoặc MANAGER |

---

### 2.2. Lấy danh sách tất cả loại thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả loại thuốc |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.3. Tìm kiếm loại thuốc (Phân trang)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories/search` |
| **Method** | `GET` |
| **URL Params** | tenLoai=[string] - Tên loại thuốc (optional)<br>page=[number] - Số trang (default: 0)<br>size=[number] - Số phần tử mỗi trang (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách loại thuốc có phân trang |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.4. Lấy thông tin loại thuốc theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories/{maLoai}` |
| **Method** | `GET` |
| **URL Params** | maLoai=[string] - Mã loại thuốc |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết loại thuốc |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy loại thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.5. Cập nhật loại thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories/{maLoai}` |
| **Method** | `PUT` |
| **URL Params** | maLoai=[string] - Mã loại thuốc cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maLoai": "string",<br>&nbsp;&nbsp;"tenLoai": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin loại thuốc đã cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy loại thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 2.6. Xóa loại thuốc

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/categories/{maLoai}` |
| **Method** | `DELETE` |
| **URL Params** | maLoai=[string] - Mã loại thuốc cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa loại thuốc thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy loại thuốc<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## 3. UNIT APIs (Quản lý Đơn Vị Tính)

### 3.1. Tạo đơn vị tính mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maDonVi": "string",<br>&nbsp;&nbsp;"tenDonVi": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 201 CREATED<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Tạo đơn vị tính thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maDonVi": "DV001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDonVi": "Viên",<br>&nbsp;&nbsp;&nbsp;&nbsp;"moTa": "Đơn vị tính là viên"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions<br>**Code:** 409 CONFLICT - Mã đơn vị đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Yêu cầu quyền ADMIN hoặc MANAGER |

---

### 3.2. Lấy danh sách tất cả đơn vị tính

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách tất cả đơn vị tính |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.3. Tìm kiếm đơn vị tính (Phân trang)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units/search` |
| **Method** | `GET` |
| **URL Params** | keyword=[string] - Từ khóa tìm kiếm (default: "")<br>page=[number] - Số trang (default: 0)<br>size=[number] - Số phần tử mỗi trang (default: 10) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Danh sách đơn vị tính có phân trang |
| **Error Response** | **Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.4. Lấy thông tin đơn vị tính theo mã

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units/{maDonVi}` |
| **Method** | `GET` |
| **URL Params** | maDonVi=[string] - Mã đơn vị tính |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin chi tiết đơn vị tính |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy đơn vị tính<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.5. Cập nhật đơn vị tính

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units/{maDonVi}` |
| **Method** | `PUT` |
| **URL Params** | maDonVi=[string] - Mã đơn vị tính cần cập nhật |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN, MANAGER |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"maDonVi": "string",<br>&nbsp;&nbsp;"tenDonVi": "string",<br>&nbsp;&nbsp;"moTa": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:** Thông tin đơn vị tính đã cập nhật |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 404 NOT FOUND - Không tìm thấy đơn vị tính<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | (none) |

---

### 3.6. Xóa đơn vị tính

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/drugs/units/{maDonVi}` |
| **Method** | `DELETE` |
| **URL Params** | maDonVi=[string] - Mã đơn vị tính cần xóa |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token}<br>**Role required:** ADMIN |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Xóa đơn vị tính thành công"<br>} |
| **Error Response** | **Code:** 404 NOT FOUND - Không tìm thấy đơn vị tính<br>**Code:** 401 UNAUTHORIZED - Missing or invalid token<br>**Code:** 403 FORBIDDEN - Insufficient permissions |
| **Sample Call** | (none) |
| **Notes** | Chỉ ADMIN mới có quyền xóa |

---

## 4. HEALTH CHECK API

### 4.1. Health Check

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8082/health` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"status": "UP",<br>&nbsp;&nbsp;"message": "Drug Service is running",<br>&nbsp;&nbsp;"timestamp": 1735315200000<br>} |
| **Error Response** | (none) |
| **Sample Call** | (none) |
| **Notes** | API kiểm tra trạng thái hoạt động của service |

---

## THÔNG TIN BỔ SUNG

### Authentication
Tất cả các API (trừ Health Check) đều yêu cầu JWT token trong header:
```
Authorization: Bearer {token}
```

### Authorization Roles
- **ADMIN**: Toàn quyền (tạo, sửa, xóa tất cả)
- **MANAGER**: Quản lý (tạo, sửa thuốc, loại thuốc, đơn vị tính)
- **USER**: Người dùng (xem thông tin, cập nhật tồn kho)
- **STAFF**: Nhân viên (xem thông tin, cập nhật tồn kho)

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
