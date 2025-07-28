package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUser(User user);
}
