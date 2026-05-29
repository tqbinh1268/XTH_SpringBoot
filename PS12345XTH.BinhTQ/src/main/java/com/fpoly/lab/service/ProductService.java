package com.fpoly.lab.service;

import com.fpoly.lab.model.Product;
import com.fpoly.lab.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Đăng ký Class này đóng vai trò tầng chứa đựng logic tính toán nghiệp vụ
public class ProductService {

    private final ProductRepository productRepository;

    // Kỹ thuật Constructor Injection bắt buộc giúp tự động nạp (tiêm) Bean phụ thuộc từ IoC Container
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product addProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Lỗi: Giá sản phẩm bán ra thị trường không được phép mang giá trị âm");
        }
        productRepository.save(product);
        return product;
    }
}