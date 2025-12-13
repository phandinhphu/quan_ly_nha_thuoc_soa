-- Seed data for Drug Service

-- Loại thuốc
INSERT INTO loai_thuoc (ma_loai, ten_loai, mo_ta) VALUES 
('LT001', 'Kháng sinh', 'Thuốc điều trị nhiễm khuẩn'),
('LT002', 'Giảm đau - Hạ sốt', 'Thuốc giảm đau và hạ nhiệt'),
('LT003', 'Vitamin - Khoáng chất', 'Bổ sung vitamin và khoáng chất'),
('LT004', 'Tiêu hóa', 'Hỗ trợ tiêu hóa'),
('LT005', 'Tim mạch', 'Điều trị bệnh tim mạch');

-- Đơn vị tính
INSERT INTO don_vi_tinh (ma_don_vi, ten_don_vi, mo_ta) VALUES 
('DV001', 'Viên', 'Thuốc dạng viên nén'),
('DV002', 'Vỉ', 'Vỉ thuốc 10 viên'),
('DV003', 'Hộp', 'Hộp thuốc'),
('DV004', 'Chai', 'Chai thuốc dạng lỏng'),
('DV005', 'Tuýp', 'Tuýp thuốc bôi');

-- Thuốc (mẫu)
INSERT INTO thuoc (ma_thuoc, ten_thuoc, ma_loai, ma_don_vi, gia_nhap, gia_ban, han_su_dung, nha_san_xuat, so_luong_ton, mo_ta) VALUES 
('TH001', 'Amoxicillin 500mg', 'LT001', 'DV002', 5000, 8000, '2026-12-31', 'Pharbaco', 100, 'Kháng sinh nhóm Penicillin'),
('TH002', 'Paracetamol 500mg', 'LT002', 'DV002', 2000, 3500, '2026-06-30', 'Pymepharco', 200, 'Thuốc giảm đau hạ sốt'),
('TH003', 'Vitamin C 1000mg', 'LT003', 'DV002', 15000, 25000, '2026-12-31', 'DHG Pharma', 150, 'Tăng cường sức đề kháng'),
('TH004', 'Kremil S', 'LT004', 'DV002', 3000, 5000, '2026-09-30', 'Delpharm', 80, 'Điều trị đau dạ dày'),
('TH005', 'Aspirin 100mg', 'LT005', 'DV002', 4000, 6500, '2026-12-31', 'Domesco', 120, 'Chống đông máu');
