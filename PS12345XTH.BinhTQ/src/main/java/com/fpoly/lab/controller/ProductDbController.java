package com.fpoly.lab.controller;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.ProductDb;
import com.fpoly.lab.service.ProductDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db/products")
public class ProductDbController {

    private final ProductDbService productDbService;

    public ProductDbController(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDb>> getAllProducts() {
        return ResponseEntity.ok(productDbService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<ProductDb> productOptional = productDbService.getProductById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm mã ID: " + id);
    }

    @PostMapping
    public ResponseEntity<ProductDb> createProduct(@RequestBody ProductDb newProduct) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productDbService.addProduct(newProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDb updatedProduct) {
        try {
            ProductDb result = productDbService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productDbService.deleteProduct(id)) {
            return ResponseEntity.ok("Xóa bỏ thành công sản phẩm ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xóa thất bại. Không tìm thấy ID.");
    }

    @GetMapping("/top")
    public ResponseEntity<List<ProductDb>> getTopProducts(@RequestParam(value = "n", defaultValue = "5") int n) {
        return ResponseEntity.ok(productDbService.getTopNProducts(n));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ProductStatistics> getProductStatistics() {
        return ResponseEntity.ok(productDbService.getStatistics());
    }
}