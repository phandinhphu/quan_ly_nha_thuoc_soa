# Hướng Dẫn Test Sales Service

## Yêu Cầu Trước Khi Test

1. **Đảm bảo các service đã chạy:**
   - Eureka Server (port 8761)
   - Auth Service (port 8081)
   - Drug Service (port 8082)
   - Inventory Service (port 8083)
   - Sales Service (port 8085)

2. **Cần có dữ liệu mẫu:**
   - Ít nhất 1 loại thuốc (LoaiThuoc)
   - Ít nhất 1 đơn vị tính (DonViTinh)
   - Ít nhất 1 thuốc (Thuoc) với số lượng tồn kho > 0
   - Tài khoản user để đăng nhập lấy token

## Các Bước Test

### Bước 1: Đăng Nhập Lấy Token

**Endpoint:** `POST http://localhost:8081/api/auth/login`

**Request Body:**
```json
{
    "tenDangNhap": "admin",
    "matKhau": "admin123"
}
```

**Response:** Copy token từ field `data.token`

**Lưu token vào biến `token` trong Postman để dùng cho các request sau.**

---

### Bước 2: Chuẩn Bị Dữ Liệu (Nếu chưa có)

#### 2.1. Tạo Loại Thuốc

**Endpoint:** `POST http://localhost:8082/api/drugs/categories`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
    "maLoai": "LT001",
    "tenLoai": "Thuốc kháng sinh",
    "moTa": "Các loại thuốc kháng sinh"
}
```

#### 2.2. Tạo Đơn Vị Tính

**Endpoint:** `POST http://localhost:8082/api/drugs/units`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
    "maDonVi": "DV001",
    "tenDonVi": "Viên",
    "moTa": "Đơn vị viên"
}
```

#### 2.3. Tạo Thuốc

**Endpoint:** `POST http://localhost:8082/api/drugs`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
    "maThuoc": "T001",
    "tenThuoc": "Paracetamol 500mg",
    "maLoai": "LT001",
    "maDonVi": "DV001",
    "giaNhap": 50000,
    "giaBan": 75000,
    "hanSuDung": "2025-12-31",
    "nhaSanXuat": "Dược phẩm ABC",
    "soLuongTon": 100,
    "moTa": "Thuốc giảm đau, hạ sốt"
}
```

**Lưu ý:** Đảm bảo `soLuongTon` > 0 để có thể test bán hàng.

---

### Bước 3: Test Sales Service

#### 3.1. Health Check

**Endpoint:** `GET http://localhost:8085/actuator/health`

**Headers:** Không cần

**Expected Response:**
```json
{
    "status": "UP",
    "service": "sales-service"
}
```

---

#### 3.2. Tạo Hóa Đơn Bán Hàng

**Endpoint:** `POST http://localhost:8085/api/sales/invoices`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
    "maHoaDon": "HD001",
    "ngayBan": "2024-01-15T10:30:00",
    "maNV": "NV001",
    "chiTiet": [
        {
            "maThuoc": "T001",
            "soLuong": 5,
            "donGia": 75000
        }
    ]
}
```

**Expected Response (201 Created):**
```json
{
    "success": true,
    "message": "Tạo hóa đơn thành công",
    "data": {
        "maHoaDon": "HD001",
        "ngayBan": "2024-01-15T10:30:00",
        "maNV": "NV001",
        "tongTien": 375000,
        "chiTiet": [
            {
                "maThuoc": "T001",
                "soLuong": 5,
                "donGia": 75000
            }
        ]
    }
}
```

**Kiểm tra:**
- Hóa đơn được tạo thành công
- Tổng tiền được tính đúng (5 × 75000 = 375000)
- Tồn kho của thuốc T001 giảm từ 100 xuống 95 (kiểm tra qua drug-service)
- Lịch sử tồn kho được tạo trong inventory-service

---

#### 3.3. Lấy Danh Sách Hóa Đơn

**Endpoint:** `GET http://localhost:8085/api/sales/invoices`

**Headers:**
```
Authorization: Bearer {token}
```

**Expected Response:**
```json
{
    "success": true,
    "message": "Lấy danh sách hóa đơn thành công",
    "data": [
        {
            "maHoaDon": "HD001",
            "ngayBan": "2024-01-15T10:30:00",
            "maNV": "NV001",
            "tongTien": 375000,
            "chiTiet": [...]
        }
    ]
}
```

---

#### 3.4. Lấy Chi Tiết Hóa Đơn

**Endpoint:** `GET http://localhost:8085/api/sales/invoices/HD001`

**Headers:**
```
Authorization: Bearer {token}
```

**Expected Response:**
```json
{
    "success": true,
    "message": "Lấy thông tin hóa đơn thành công",
    "data": {
        "maHoaDon": "HD001",
        "ngayBan": "2024-01-15T10:30:00",
        "maNV": "NV001",
        "tongTien": 375000,
        "chiTiet": [...]
    }
}
```

---

#### 3.5. Test Trường Hợp Lỗi

##### 3.5.1. Bán Thuốc Không Tồn Tại

**Request Body:**
```json
{
    "maHoaDon": "HD002",
    "ngayBan": "2024-01-15T10:30:00",
    "maNV": "NV001",
    "chiTiet": [
        {
            "maThuoc": "T999",
            "soLuong": 5,
            "donGia": 75000
        }
    ]
}
```

**Expected Response (404 Not Found):**
```json
{
    "success": false,
    "message": "Không tìm thấy thuốc với mã: T999"
}
```

##### 3.5.2. Bán Vượt Quá Số Lượng Tồn Kho

**Request Body:**
```json
{
    "maHoaDon": "HD003",
    "ngayBan": "2024-01-15T10:30:00",
    "maNV": "NV001",
    "chiTiet": [
        {
            "maThuoc": "T001",
            "soLuong": 1000,
            "donGia": 75000
        }
    ]
}
```

**Expected Response (400 Bad Request):**
```json
{
    "success": false,
    "message": "Thuốc T001 không đủ số lượng trong kho"
}
```

##### 3.5.3. Tạo Hóa Đơn Trùng Mã

**Request Body:**
```json
{
    "maHoaDon": "HD001",
    "ngayBan": "2024-01-15T10:30:00",
    "maNV": "NV001",
    "chiTiet": [...]
}
```

**Expected Response (400 Bad Request):**
```json
{
    "success": false,
    "message": "Mã hóa đơn đã tồn tại"
}
```

---

#### 3.6. Xóa Hóa Đơn (Chỉ ADMIN)

**Endpoint:** `DELETE http://localhost:8085/api/sales/invoices/HD001`

**Headers:**
```
Authorization: Bearer {token}
```

**Expected Response:**
```json
{
    "success": true,
    "message": "Xóa hóa đơn thành công",
    "data": null
}
```

---

## Test Với Nhiều Sản Phẩm

**Request Body:**
```json
{
    "maHoaDon": "HD004",
    "ngayBan": "2024-01-15T14:00:00",
    "maNV": "NV001",
    "chiTiet": [
        {
            "maThuoc": "T001",
            "soLuong": 10,
            "donGia": 75000
        },
        {
            "maThuoc": "T002",
            "soLuong": 5,
            "donGia": 120000
        },
        {
            "maThuoc": "T003",
            "soLuong": 3,
            "donGia": 150000
        }
    ]
}
```

**Kiểm tra:**
- Tổng tiền = (10 × 75000) + (5 × 120000) + (3 × 150000) = 750000 + 600000 + 450000 = 1800000
- Tất cả các thuốc đều giảm tồn kho đúng
- Tất cả đều có lịch sử tồn kho

---

## Kiểm Tra Tích Hợp

### 1. Kiểm Tra Tồn Kho Đã Giảm

**Endpoint:** `GET http://localhost:8082/api/drugs/T001`

**Headers:**
```
Authorization: Bearer {token}
```

Kiểm tra `soLuongTon` đã giảm đúng chưa.

### 2. Kiểm Tra Lịch Sử Tồn Kho

**Endpoint:** `GET http://localhost:8083/api/inventory-history`

**Headers:**
```
Authorization: Bearer {token}
```

Tìm các bản ghi có `lyDo` chứa "Bán hàng từ hóa đơn HD001".

---

## Sử Dụng Postman Collection

1. Import file `PMA-Sales-Service.postman_collection.json` vào Postman
2. Set biến `baseUrl` = `http://localhost:8085`
3. Đăng nhập và lấy token, set vào biến `token`
4. Chạy các request theo thứ tự

---

## Lưu Ý

1. **Token:** Mỗi request cần có token hợp lệ (trừ health check)
2. **Tồn Kho:** Đảm bảo thuốc có đủ số lượng trước khi bán
3. **Mã Hóa Đơn:** Phải unique, không được trùng
4. **Validation:** Kiểm tra các trường bắt buộc (maHoaDon, ngayBan, maNV, chiTiet)
5. **Role:** 
   - Tạo hóa đơn: ADMIN, MANAGER, STAFF
   - Xóa hóa đơn: Chỉ ADMIN

---

## Troubleshooting

### Lỗi: "Không tìm thấy thuốc"
- Kiểm tra thuốc đã được tạo trong drug-service chưa
- Kiểm tra mã thuốc có đúng không

### Lỗi: "Không đủ số lượng trong kho"
- Kiểm tra `soLuongTon` của thuốc trong drug-service
- Có thể cần nhập thêm hàng qua supplier-service

### Lỗi: "Token không hợp lệ"
- Đăng nhập lại để lấy token mới
- Kiểm tra token có hết hạn chưa

### Lỗi: "Service không khả dụng"
- Kiểm tra tất cả services đã chạy chưa
- Kiểm tra Eureka Server đã register services chưa
- Kiểm tra network connectivity giữa các services

