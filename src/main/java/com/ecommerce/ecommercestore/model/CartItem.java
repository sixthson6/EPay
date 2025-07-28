package com.ecommerce.ecommercestore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart_items")
@Data
public class CartItem {
    @Id
    private String id;
    private Product product;
    private Integer quantity;
    private Cart cart;
}
