package com.fpoly.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    // mappedBy: Trỏ chính xác tới tên của biến khai báo nằm bên trong lớp con (ProductDb)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Ngắt circular reference - không serialize danh sách products
    private List<ProductDb> products;
}