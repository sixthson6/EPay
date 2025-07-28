package com.ecommerce.ecommercestore.payload.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String brandName;
    private String categoryName;
}
