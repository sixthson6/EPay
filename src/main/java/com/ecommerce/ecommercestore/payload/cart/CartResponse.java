package com.ecommerce.ecommercestore.payload.cart;

import lombok.Data;
import java.util.List;

@Data
public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items;
    private Double total;
}
