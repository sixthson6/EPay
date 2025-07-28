package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.model.CartItem;
import com.ecommerce.ecommercestore.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends MongoRepository<CartItem, String> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findAllByProduct(Product product);

    void deleteAllByCart(Cart cart);
}
