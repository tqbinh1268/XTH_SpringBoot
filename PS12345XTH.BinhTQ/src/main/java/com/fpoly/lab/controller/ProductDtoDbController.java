package com.fpoly.lab.controller;

import com.fpoly.lab.dto.ProductDTO;
import com.fpoly.lab.service.ProductDtoDbService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db/dto/products") // Endpoint riêng cho luồng DTO
@CrossOrigin("*")
public class ProductDtoDbController {

    private final ProductDtoDbService productDtoDbService;

    public ProductDtoDbController(ProductDtoDbService productDtoDbService) {
        this.productDtoDbService = productDtoDbService;
    }

    /**
     * API trả về danh sách tất cả sản phẩm dưới dạng DTO.
     * So sánh với /db/products để thấy sự khác biệt.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProductsAsDto() {
        return ResponseEntity.ok(productDtoDbService.getAllProductsAsDto());
    }

    /**
     * API trả về một sản phẩm theo ID dưới dạng DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByIdAsDto(@PathVariable Long id) {
        Optional<ProductDTO> productDtoOptional = productDtoDbService.getProductByIdAsDto(id);
        if (productDtoOptional.isPresent()) {
            return ResponseEntity.ok(productDtoOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm mã ID: " + id);
    }

    /**
     * API trả về danh sách sản phẩm được phân trang dưới dạng DTO.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getProductsPageAsDto(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(productDtoDbService.getProductsWithPaginationAsDto(pageable));
    }
}