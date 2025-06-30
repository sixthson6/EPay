package com.ecommerce.ecommercestore.controller;

import com.ecommerce.ecommercestore.model.enums.CategoryName;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import com.ecommerce.ecommercestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id){
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @RequestParam(value = "name", required = false) String nameFilter
    ){
        List<ProductResponse> products = productService.getAllProducts(pageNo, pageSize, sortBy, sortDir, nameFilter);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(@PathVariable("categoryName") String categoryName){
        List<ProductResponse> products = productService.getProductsByCategoryName(CategoryName.valueOf(categoryName));
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,
                                                         @Valid @RequestBody ProductRequest productRequest){
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
    }
}
