package com.fpoly.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    // Chỉ chứa các thông tin an toàn muốn public ra ngoài cho Client
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    //tổng tiền
    private Double total;

}