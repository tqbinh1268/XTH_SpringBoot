package com.fpoly.lab.service;

import com.fpoly.lab.dto.ProductDTO;
import com.fpoly.lab.model.ProductDb;
import com.fpoly.lab.repository.ProductDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Tự động nạp tích hợp biến Logger mang tên định danh chuẩn "log" của thư viện SLF4J
public class ProductBackupService {

    private final ProductDbRepository repository;

    public ProductBackupService(ProductDbRepository repository) {
        this.repository = repository;
    }

    public ProductDTO getProductDtoInfo(Long id) {
        log.info("Hệ thống bắt đầu kích hoạt lệnh truy vấn tìm kiếm sản phẩm mang mã số ID: {}", id);

        ProductDb product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Lỗi nghiêm trọng: Không quét thấy bất kỳ bản ghi nào trùng khớp mã ID: {}", id);
                    return new RuntimeException("Sản phẩm yêu cầu không tồn tại trên hệ thống DB.");
                });

        // Quy trình thực hiện chuyển đổi ánh xạ tay Mapping từ đối tượng Entity sang mô hình bọc bảo mật DTO
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        log.info("Quy trình đóng gói bọc chuyển đổi dữ liệu sang DTO hoàn tất thành công cho sản phẩm: {}", dto.getName());
        return dto;
    }
}