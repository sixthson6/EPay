package com.ecommerce.ecommercestore.controller;

import com.ecommerce.ecommercestore.payload.product.ProductDetailResponse;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductSummaryResponse;
import com.ecommerce.ecommercestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductDetailResponse createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllProducts() {
        List<ProductSummaryResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable("id") String productId) {
        ProductDetailResponse product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> updateProduct(@PathVariable("id") String productId, @RequestBody ProductRequest productRequest) {
        ProductDetailResponse updatedProduct = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
