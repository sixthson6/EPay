package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.payload.product.ProductDetailResponse;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import com.ecommerce.ecommercestore.payload.product.ProductSummaryResponse;

import java.util.List;

public interface ProductService {

    ProductDetailResponse createProduct(ProductRequest productRequest);

    List<ProductSummaryResponse> getAllProducts();

    ProductDetailResponse getProductById(String productId);

    ProductDetailResponse updateProduct(String productId, ProductRequest productRequest);

    void deleteProduct(String productId);

    ProductResponse mapToDTO(Product product);
}