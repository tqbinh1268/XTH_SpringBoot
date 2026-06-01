package com.fpoly.lab.repository;

import com.fpoly.lab.model.ProductDb; // Đã đổi sang ProductDb
import com.fpoly.lab.dto.ProductStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDbRepository extends JpaRepository<ProductDb, Long> {

    // 💡 Chú ý: FROM ProductDb (khớp hoàn toàn với tên Class Entity mới)
    @Query("SELECT new com.fpoly.lab.dto.ProductStatistics(COUNT(p), AVG(p.price), MAX(p.price), MIN(p.price)) FROM ProductDb p")
    ProductStatistics getStatistics();
}