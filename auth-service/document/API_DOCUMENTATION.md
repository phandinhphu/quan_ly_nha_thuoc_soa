# AUTH SERVICE - API DOCUMENTATION

## BASE URL
```
http://localhost:8080/api/auth
```

---

## 1. AUTHENTICATION APIs (Xác thực & Ủy quyền)

### 1.1. Đăng ký tài khoản mới

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/auth/register` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"tenDangNhap": "string",<br>&nbsp;&nbsp;"matKhau": "string",<br>&nbsp;&nbsp;"hoTen": "string",<br>&nbsp;&nbsp;"email": "string",<br>&nbsp;&nbsp;"soDienThoai": "string",<br>&nbsp;&nbsp;"vaiTro": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Đăng ký thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 409 CONFLICT - Tên đăng nhập đã tồn tại |
| **Sample Call** | (none) |
| **Notes** | Không yêu cầu authentication. VaiTro có thể là: ADMIN, MANAGER, USER, STAFF |

---

### 1.2. Đăng nhập

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/auth/login` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"tenDangNhap": "string",<br>&nbsp;&nbsp;"matKhau": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Đăng nhập thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Validation error<br>**Code:** 401 UNAUTHORIZED - Sai tên đăng nhập hoặc mật khẩu |
| **Sample Call** | (none) |
| **Notes** | Token trả về cần được lưu trữ và sử dụng cho các request sau |

---

### 1.3. Xác thực token (POST)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/auth/verify` |
| **Method** | `POST` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json |
| **Data Params** | json<br>{<br>&nbsp;&nbsp;"token": "string"<br>} |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Token hợp lệ",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maNguoiDung": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDangNhap": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"hoTen": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soDienThoai": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"vaiTro": "string"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Token không hợp lệ<br>**Code:** 401 UNAUTHORIZED - Token hết hạn hoặc không đúng |
| **Sample Call** | (none) |
| **Notes** | API dùng để các service khác xác thực token |

---

### 1.4. Xác thực token (GET - sử dụng Header)

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/auth/verify` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Token hợp lệ",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maNguoiDung": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDangNhap": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"hoTen": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soDienThoai": "string",<br>&nbsp;&nbsp;&nbsp;&nbsp;"vaiTro": "string"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Token không hợp lệ<br>**Code:** 401 UNAUTHORIZED - Token hết hạn hoặc không đúng |
| **Sample Call** | (none) |
| **Notes** | Alternative API sử dụng Authorization header thay vì body |

---

### 1.5. Lấy thông tin người dùng hiện tại

| **Field** | **Value** |
|-----------|-----------|
| **URL** | `http://localhost:8080/api/auth/get/me` |
| **Method** | `GET` |
| **URL Params** | (none) |
| **Header** | Content-Type: application/json<br>Authorization: Bearer {token} |
| **Data Params** | (none) |
| **Success Response** | **Code:** 200 OK<br>**Content:**<br>json<br>{<br>&nbsp;&nbsp;"success": true,<br>&nbsp;&nbsp;"message": "Lấy thông tin người dùng thành công",<br>&nbsp;&nbsp;"data": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"maNguoiDung": "ND001",<br>&nbsp;&nbsp;&nbsp;&nbsp;"tenDangNhap": "admin",<br>&nbsp;&nbsp;&nbsp;&nbsp;"hoTen": "Nguyễn Văn A",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "admin@example.com",<br>&nbsp;&nbsp;&nbsp;&nbsp;"soDienThoai": "0901234567",<br>&nbsp;&nbsp;&nbsp;&nbsp;"vaiTro": "ADMIN"<br>&nbsp;&nbsp;}<br>} |
| **Error Response** | **Code:** 400 BAD REQUEST - Token không hợp lệ<br>**Code:** 401 UNAUTHORIZED - Token hết hạn hoặc không đúng |
| **Sample Call** | (none) |
| **Notes** | API dùng để lấy thông tin user đang đăng nhập |

---

## THÔNG TIN BỔ SUNG

### Authentication Flow
1. **Đăng ký**: Client gọi `/api/auth/register` với thông tin user → Nhận token
2. **Đăng nhập**: Client gọi `/api/auth/login` với credentials → Nhận token
3. **Sử dụng token**: Gửi token trong header `Authorization: Bearer {token}` cho các request khác
4. **Xác thực**: Các service khác gọi `/api/auth/verify` để xác thực token

### Token Information
- **Type**: JWT (JSON Web Token)
- **Expiration**: Token có thời hạn sử dụng (cấu hình trong application.yml)
- **Format**: `Bearer {token}` trong Authorization header
- **Payload**: Chứa thông tin user (maNguoiDung, tenDangNhap, vaiTro)

### User Roles
- **ADMIN**: Quản trị viên - Toàn quyền
- **MANAGER**: Quản lý - Quản lý thuốc, nhà cung cấp, phiếu nhập
- **STAFF**: Nhân viên - Xem thông tin và bán hàng

### Common Error Codes
- **400 BAD REQUEST**: Dữ liệu không hợp lệ (validation error)
- **401 UNAUTHORIZED**: Chưa đăng nhập, token không hợp lệ hoặc hết hạn
- **403 FORBIDDEN**: Không có quyền truy cập
- **409 CONFLICT**: Dữ liệu bị trùng lặp (tên đăng nhập đã tồn tại)
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

### Security Notes
- Mật khẩu được mã hóa bằng BCrypt trước khi lưu database
- Token được ký bằng secret key (cấu hình trong application.yml)
- Token chỉ có thể được giải mã bởi auth-service
- Không bao giờ gửi mật khẩu trong response
