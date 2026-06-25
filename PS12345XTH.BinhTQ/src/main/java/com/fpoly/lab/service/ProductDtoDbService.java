package com.fpoly.lab.service;

import com.fpoly.lab.dto.ProductDTO;
import com.fpoly.lab.model.ProductDb;
import com.fpoly.lab.repository.ProductDbRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDtoDbService {

    private final ProductDbRepository productDbRepository;

    public ProductDtoDbService(ProductDbRepository productDbRepository) {
        this.productDbRepository = productDbRepository;
    }

    // --- Phương thức chuyển đổi private ---
    private ProductDTO convertToDto(ProductDb productDb) {
        return new ProductDTO(
                productDb.getId(),
                productDb.getName(),
                productDb.getPrice(),
                productDb.getQuantity(),
                productDb.getPrice() * productDb.getQuantity()
        );
    }

    // --- Các phương thức public trả về DTO ---

    /**
     * Lấy tất cả sản phẩm dưới dạng DTO.
     */
    public List<ProductDTO> getAllProductsAsDto() {
        return productDbRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy một sản phẩm theo ID dưới dạng DTO.
     */
    public Optional<ProductDTO> getProductByIdAsDto(Long id) {
        return productDbRepository.findById(id).map(this::convertToDto);
    }

    /**
     * Lấy danh sách sản phẩm được phân trang dưới dạng DTO.
     */
    public Page<ProductDTO> getProductsWithPaginationAsDto(Pageable pageable) {
        Page<ProductDb> productDbPage = productDbRepository.findAll(pageable);
        List<ProductDTO> dtoList = productDbPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, productDbPage.getTotalElements());
    }
}