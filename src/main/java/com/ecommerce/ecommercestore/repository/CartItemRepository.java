package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.model.CartItem;
import com.ecommerce.ecommercestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
