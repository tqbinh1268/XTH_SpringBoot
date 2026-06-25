package com.fpoly.lab.service;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.ProductDb;
import com.fpoly.lab.repository.ProductDbRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductDbService {

    private final ProductDbRepository productDbRepository;

    public ProductDbService(ProductDbRepository productDbRepository) {
        this.productDbRepository = productDbRepository;
    }

    public List<ProductDb> getAllProducts() {
        return productDbRepository.findAll();
    }

    public Optional<ProductDb> getProductById(Long id) {
        return productDbRepository.findById(id);
    }

    public ProductDb addProduct(ProductDb product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Lỗi: Giá sản phẩm không được âm");
        }
        return productDbRepository.save(product);
    }

    public ProductDb updateProduct(Long id, ProductDb updatedProduct) {
        ProductDb existingProduct = productDbRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return productDbRepository.save(existingProduct);
    }

    public boolean deleteProduct(Long id) {
        if (productDbRepository.existsById(id)) {
            productDbRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductDb> getTopNProducts(int n) {
        return productDbRepository.findAll(PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "price"))).getContent();
    }

    public ProductStatistics getStatistics() {
        if (productDbRepository.count() == 0) {
            return new ProductStatistics(0, 0.0, 0.0, 0.0);
        }
        return productDbRepository.getStatistics();
    }

    public Page<ProductDb> getProductsWithPagination(int page, int size, String sortBy) {
        // Thiết lập PageRequest hỗ trợ chỉ số trang bắt đầu từ 0, giới hạn số lượng và sắp xếp tăng dần tự động
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return productDbRepository.findAll(pageable);
    }
}