package com.fpoly.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatistics {
    private long totalCount;
    private double averagePrice;
    private double maxPrice;
    private double minPrice;
}