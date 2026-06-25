package com.fpoly.lab.service;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.Product;
import com.fpoly.lab.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Lỗi: Giá sản phẩm bán ra thị trường không được phép mang giá trị âm");
        }
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thất bại. Không thấy ID."));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return existingProduct;
    }

    public boolean deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public List<Product> getTopNProducts(int n) {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()))
                .limit(n)
                .toList();
    }

    public ProductStatistics getStatistics() {
        return productRepository.getStatistics();
    }
}