package com.ecommerce.ecommercestore.payload.cart;

import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import lombok.Data;

@Data
public class CartItemResponse {
    private String id;
    private ProductResponse product;
    private Integer quantity;
    private Double subtotal;
}