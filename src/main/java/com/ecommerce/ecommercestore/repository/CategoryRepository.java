package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
