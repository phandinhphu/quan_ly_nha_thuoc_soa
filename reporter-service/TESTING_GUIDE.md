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

#### 3.3. Xem tất cả doanh thu

**Endpoint:** `GET http://localhost:8086/api/reports/summary`

**Headers:**

```
Authorization: Bearer {token}
```

**Expected Response:**

```json
{
  "totalInvoices": 3,
  "totalRevenue": 2275000.0,
  "topSellingDrugs": [
    {
      "maThuoc": "T101",
      "tenThuoc": "Paracetamol 500mg",
      "soLuongDaBan": 25
    }
  ]
}
```

---

#### 3.4. Xem doanh thu theo ngày tháng năm

**Endpoint:** `GET http://localhost:8086/api/reports/summary?date=2024`
**Endpoint:** `GET http://localhost:8086/api/reports/summary?date=2024-01-15`
**Headers:**

```
Authorization: Bearer {token}
```

**Expected Response:**

```json
{
  "totalInvoices": 2,
  "totalRevenue": 1325000.0,
  "topSellingDrugs": [
    {
      "maThuoc": "T101",
      "tenThuoc": "Paracetamol 500mg",
      "soLuongDaBan": 15
    }
  ]
}
```
