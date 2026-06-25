package com.fpoly.lab.repository;

import com.fpoly.lab.dto.ProductStatistics;
import com.fpoly.lab.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class ProductRepository {
    private final List<Product> list = new ArrayList<>();
    private final Random random = new Random();

    public ProductRepository() {
        list.add(new Product(1L, "Sách Java core", 150000.0, random.nextInt(101)));
        list.add(new Product(2L, "Laptop Gaming", 25500000.0, random.nextInt(101)));
    }

    public List<Product> findAll() { return list; }

    public Optional<Product> findById(Long id) {
        return list.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void save(Product product) { list.add(product); }

    public boolean deleteById(Long id) {
        return list.removeIf(p -> p.getId().equals(id));
    }

    public ProductStatistics getStatistics() {
        if (list.isEmpty()) {
            return new ProductStatistics(0, 0.0, 0.0, 0.0);
        }
        java.util.DoubleSummaryStatistics stats = list.stream()
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
        return new ProductStatistics(
                stats.getCount(),
                stats.getAverage(),
                stats.getMax(),
                stats.getMin()
        );
    }
}