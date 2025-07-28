package com.ecommerce.ecommercestore.payload.product;

import com.ecommerce.ecommercestore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String brand;
    private Category category;
    private Map<String, Object> attributes;
}
