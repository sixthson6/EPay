package com.ecommerce.ecommercestore.payload.product;

import com.ecommerce.ecommercestore.model.enums.CategoryName;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    private String description;

    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;

    private String imageUrl;

    @NotNull(message = "Category name cannot be null")
    private CategoryName categoryName;
}


