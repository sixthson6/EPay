package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
