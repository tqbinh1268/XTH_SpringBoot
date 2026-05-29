package com.fpoly.lab.repository;

import com.fpoly.lab.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // Đăng ký Class này thành một Spring Bean chịu trách nhiệm quản lý kho dữ liệu của hệ thống
public class ProductRepository {
    private final List<Product> list = new ArrayList<>();

    public ProductRepository() {
        list.add(new Product(1L, "Sách Java core", 150000.0));
    }

    public List<Product> findAll() { return list; }
    public Optional<Product> findById(Long id) {
        return list.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
    public void save(Product product) { list.add(product); }
}