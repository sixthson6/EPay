package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(Category category);
}
