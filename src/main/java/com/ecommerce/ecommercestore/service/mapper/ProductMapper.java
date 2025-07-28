package com.ecommerce.ecommercestore.service.mapper;

import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.payload.product.ProductDetailResponse;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import com.ecommerce.ecommercestore.payload.product.ProductSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDetailResponse toProductDetailResponse(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getBrand(),
                product.getCategory(),
                product.getAttributes()
        );
    }

    public ProductSummaryResponse toProductSummaryResponse(Product product) {
        if (product == null) {
            return null;
        }
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;

        return new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getBrand(),
                categoryName
        );
    }

    public ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setCategory(product.getCategory());
        return productResponse;
    }

    public Product toProduct(ProductRequest request) {
        if (request == null) {
            return null;
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setAttributes(request.getAttributes());
        product.setBrand(request.getBrand());
        // Note: Setting Category will be handled in the service layer
        // after fetching it from the database using the ID from the request.
        return product;
    }
}