package com.fpoly.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products") // Vẫn giữ liên kết tới tên bảng "products" dưới MySQL
public class ProductDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "product_name", nullable = false, length = 150)
//    private String name;
//
//    @Column(nullable = false)
//    private Double price;

    @NotBlank(message = "Lỗi: Trường tên sản phẩm không được phép để rỗng hoặc nhập khoảng trắng đơn thuần")
    @Size(min = 3, max = 50, message = "Lỗi: Độ dài của tên sản phẩm bắt buộc phải nằm trong giới hạn từ 3 đến 50 ký tự")
    @Column(name = "product_name", nullable = true, length = 150)
    private String name;

    @NotNull(message = "Lỗi: Giá của sản phẩm yêu cầu bắt buộc phải thực hiện nhập thông tin")
    @Min(value = 1000, message = "Lỗi: Mức định giá của sản phẩm tối thiểu phải đạt mốc giá trị từ 1000 VNĐ trở lên")
    @Column(nullable = true)
    private Double price;

    @NotNull(message = "Lỗi: Số lượng của sản phẩm yêu cầu bắt buộc phải thực hiện nhập thông tin")
    @Min(value = 0, message = "Lỗi: Mức định số lượng của sản phẩm tối thiểu phải đạt mốc giá trị từ 0 trở lên")
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Tạo cột khóa ngoại ánh xạ trực tiếp dưới bảng MySQL Database
    private Category category;
}