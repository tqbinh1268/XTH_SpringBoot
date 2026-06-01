-- 1. Tạo database nếu chưa tồn tại và chuyển sang database đó
CREATE DATABASE IF NOT EXISTS fpoly_spring_core CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE fpoly_spring_core;

-- 2. Tạo bảng categories
CREATE TABLE IF NOT EXISTS categories (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          category_name VARCHAR(100) NOT NULL,
                                          description TEXT,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Tạo bảng products (tham chiếu tới categories)
CREATE TABLE IF NOT EXISTS products (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        product_name VARCHAR(255) NOT NULL,
                                        description TEXT,
                                        price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
                                        category_id INT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Danh mục sản phẩm (ví dụ)
INSERT INTO categories (category_name) VALUES
                                           ('Điện thoại'),
                                           ('Laptop'),
                                           ('Phụ kiện');

-- 5. Sản phẩm mẫu thuộc mỗi danh mục
INSERT INTO products (product_name, price, category_id) VALUES
                                                            ('iPhone 15 Pro Max', 34990000.0, 1),
                                                            ('Samsung Galaxy S24 Ultra', 29990000.0, 1),
                                                            ('Xiaomi 14 Ultra', 22500000.0, 1),
                                                            ('MacBook Air M3', 27990000.0, 2),
                                                            ('ASUS ROG Strix G16', 38500000.0, 2),
                                                            ('Acer Nitro V', 19500000.0, 2),
                                                            ('Chuột Logitech G102', 400000.0, 3),
                                                            ('Bàn phím cơ AKKO 3098B', 1650000.0, 3),
                                                            ('Tai nghe Sony WH-1000XM5', 6500000.0, 3),
                                                            ('Sạc Dự Phòng Anker 20000mAh', 950000.0, 3);