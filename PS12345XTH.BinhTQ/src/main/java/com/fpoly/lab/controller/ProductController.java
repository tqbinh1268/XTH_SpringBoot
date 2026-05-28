package com.fpoly.lab.controller;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> productList = new ArrayList<>();

    public ProductController() {
        productList.add(new Product(1L, "Laptop Acer Nitro", 18500000.0));
        productList.add(new Product(2L, "Chuột Logitech G102", 400000.0));
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        for (Product p : productList) {
            if (p.getId().equals(id)) return ResponseEntity.ok(p);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm mã ID: " + id);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product newProduct) {
        productList.add(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        for (Product p : productList) {
            if (p.getId().equals(id)) {
                p.setName(updatedProduct.getName());
                p.setPrice(updatedProduct.getPrice());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thất bại. Không thấy ID.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        boolean removed = productList.removeIf(p -> p.getId().equals(id));
        if (removed) return ResponseEntity.ok("Xóa bỏ thành công sản phẩm ID: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xóa thất bại.");
    }

    // 6. LẤY TOP N SẢN PHẨM CÓ GIÁ CAO NHẤT (GET http://localhost:8081/api/products/top?n=5)
    @GetMapping("/top")
    public ResponseEntity<List<Product>> getTopProducts(@RequestParam(value = "n", defaultValue = "5") int n) {
        List<Product> topProducts = productList.stream()
                .sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()))
                .limit(n)
                .toList();

        return ResponseEntity.ok(topProducts);
    }

    // 7. THỐNG KÊ SẢN PHẨM (GET http://localhost:8081/api/products/statistics)
    @GetMapping("/statistics")
    public ResponseEntity<ProductStatistics> getProductStatistics() {
        // Nếu danh sách rỗng, trả về các chỉ số bằng 0 để tránh lỗi logic
        if (productList.isEmpty()) {
            return ResponseEntity.ok(new ProductStatistics(0, 0.0, 0.0, 0.0));
        }

        // Tận dụng DoubleSummaryStatistics của Stream API để tính toán siêu tốc toàn bộ chỉ số
        java.util.DoubleSummaryStatistics stats = productList.stream()
                .mapToDouble(Product::getPrice)
                .summaryStatistics();

        // Đóng gói các chỉ số thu thập được vào đối tượng phản hồi
        ProductStatistics statistics = new ProductStatistics(
                stats.getCount(),        // Tổng số lượng phần tử
                stats.getAverage(),      // Giá trị trung bình (Average)
                stats.getMax(),          // Giá trị lớn nhất (Max)
                stats.getMin()           // Giá trị nhỏ nhất (Min)
        );

        return ResponseEntity.ok(statistics);
    }
}