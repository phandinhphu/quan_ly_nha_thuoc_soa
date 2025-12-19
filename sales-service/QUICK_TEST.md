# Quick Test Guide - Sales Service

## Chạy Services

### Option 1: Docker Compose (Khuyến nghị)

```bash
# Từ thư mục root của project
docker-compose up -d
```

### Option 2: Chạy Từng Service

```bash
# Terminal 1: Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2: Auth Service
cd auth-service
mvn spring-boot:run

# Terminal 3: Drug Service
cd drug-service
mvn spring-boot:run

# Terminal 4: Inventory Service
cd inventory-service
mvn spring-boot:run

# Terminal 5: Sales Service
cd sales-service
mvn spring-boot:run
```

## Test Nhanh Với cURL

### 1. Đăng Nhập Lấy Token

```bash
TOKEN=$(curl -s -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "tenDangNhap": "admin",
    "matKhau": "admin123"
  }' | jq -r '.data.token')

echo "Token: $TOKEN"
```

### 2. Tạo Thuốc (Nếu chưa có)

```bash
curl -X POST http://localhost:8082/api/drugs/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "maLoai": "LT001",
    "tenLoai": "Thuốc kháng sinh",
    "moTa": "Các loại thuốc kháng sinh"
  }'

curl -X POST http://localhost:8082/api/drugs/units \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "maDonVi": "DV001",
    "tenDonVi": "Viên",
    "moTa": "Đơn vị viên"
  }'

curl -X POST http://localhost:8082/api/drugs \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

### 3. Health Check

```bash
curl http://localhost:8085/actuator/health
```

### 4. Tạo Hóa Đơn

```bash
curl -X POST http://localhost:8085/api/sales/invoices \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
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
  }' | jq
```

### 5. Lấy Danh Sách Hóa Đơn

```bash
curl -X GET http://localhost:8085/api/sales/invoices \
  -H "Authorization: Bearer $TOKEN" | jq
```

### 6. Lấy Chi Tiết Hóa Đơn

```bash
curl -X GET http://localhost:8085/api/sales/invoices/HD001 \
  -H "Authorization: Bearer $TOKEN" | jq
```

### 7. Kiểm Tra Tồn Kho Đã Giảm

```bash
curl -X GET http://localhost:8082/api/drugs/T001 \
  -H "Authorization: Bearer $TOKEN" | jq '.data.soLuongTon'
```

**Expected:** `95` (100 - 5 = 95)

## Test Với Postman

1. Import file `PMA-Sales-Service.postman_collection.json`
2. Set variables:
   - `baseUrl`: `http://localhost:8085`
   - `token`: Token từ bước đăng nhập
3. Chạy các request theo thứ tự

## Checklist Test

- [ ] Health check thành công
- [ ] Tạo hóa đơn thành công
- [ ] Tổng tiền tính đúng
- [ ] Tồn kho giảm đúng
- [ ] Lịch sử tồn kho được tạo
- [ ] Lấy danh sách hóa đơn thành công
- [ ] Lấy chi tiết hóa đơn thành công
- [ ] Test validation (thuốc không tồn tại)
- [ ] Test validation (không đủ số lượng)
- [ ] Test validation (mã hóa đơn trùng)

## Ports

- Eureka Server: 8761
- Auth Service: 8081
- Drug Service: 8082
- Inventory Service: 8083
- Supplier Service: 8084
- **Sales Service: 8085**

## URLs Hữu Ích

- Eureka Dashboard: http://localhost:8761
- Sales Service Health: http://localhost:8085/actuator/health
- Swagger UI (nếu có): http://localhost:8085/swagger-ui.html

