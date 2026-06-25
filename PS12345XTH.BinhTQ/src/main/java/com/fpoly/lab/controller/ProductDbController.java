package com.fpoly.lab.controller;

import com.fpoly.lab.dto.ProductDTO;
import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.ProductDb;
import com.fpoly.lab.repository.ProductDbRepository;
import com.fpoly.lab.service.ProductDbService;
import lombok.NonNull; // Import Lombok's NonNull
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db/products")
@CrossOrigin("*") // Mở CORS cho tất cả (hoặc có thể thêm "http://localhost:63342")
public class ProductDbController {

    private final ProductDbService productDbService;
    ProductDbRepository productDbRepository;

    public ProductDbController(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    @GetMapping
    public @NonNull ResponseEntity<List<ProductDb>> getAllProducts() {
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
    public @NonNull ResponseEntity<ProductDb> createProduct(@Valid @RequestBody ProductDb newProduct) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productDbService.addProduct(newProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDb updatedProduct) {
        try {
            ProductDb result = productDbService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productDbService.deleteProduct(id)) {
            return ResponseEntity.ok("Xóa bỏ thành công sản phẩm ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xóa thất bại. Không tìm thấy ID.");
    }

    @GetMapping("/top")
    public @NonNull ResponseEntity<List<ProductDb>> getTopProducts(@RequestParam(value = "n", defaultValue = "2") int n) {
        return ResponseEntity.ok(productDbService.getTopNProducts(n));
    }

    @GetMapping("/statistics")
    public @NonNull ResponseEntity<ProductStatistics> getProductStatistics() {
        return ResponseEntity.ok(productDbService.getStatistics());
    }

    @GetMapping("/page")
    public @NonNull ResponseEntity<Page<ProductDb>> getProductsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(productDbService.getProductsWithPagination(page, size, sortBy));
    }
}