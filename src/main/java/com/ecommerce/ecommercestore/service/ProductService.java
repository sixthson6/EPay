package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.model.enums.CategoryName;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    List<ProductResponse> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir, String nameFilter);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest);
    void deleteProduct(Long productId);
    ProductResponse mapToDTO(Product product);
    Product mapToEntity(ProductRequest productRequest);
    List<ProductResponse> getProductsByCategoryName(CategoryName categoryName); // Updated method signature

}
