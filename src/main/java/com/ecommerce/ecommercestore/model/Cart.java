package com.ecommerce.ecommercestore.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private User user;
    private List<CartItem> cartItems = new ArrayList<>();
}
