package com.ecommerce.ecommercestore.repository;

import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryName name);
}
