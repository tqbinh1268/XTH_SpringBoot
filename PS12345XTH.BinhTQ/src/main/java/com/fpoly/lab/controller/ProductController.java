package com.fpoly.lab.controller;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.Product;
import com.fpoly.lab.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm mã ID: " + id);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            Product result = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Xóa bỏ thành công sản phẩm ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xóa thất bại. Không tìm thấy ID.");
    }

    @GetMapping("/top")
    public ResponseEntity<List<Product>> getTopProducts(@RequestParam(value = "n", defaultValue = "5") int n) {
        return ResponseEntity.ok(productService.getTopNProducts(n));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ProductStatistics> getProductStatistics() {
        return ResponseEntity.ok(productService.getStatistics());
    }
}