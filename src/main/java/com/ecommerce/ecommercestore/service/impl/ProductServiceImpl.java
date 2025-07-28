package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.CartItem;
import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.payload.product.ProductDetailResponse;
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductSummaryResponse;
import com.ecommerce.ecommercestore.repository.CartItemRepository;
import com.ecommerce.ecommercestore.repository.CategoryRepository;
import com.ecommerce.ecommercestore.repository.ProductRepository;
import com.ecommerce.ecommercestore.service.ProductService;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import com.ecommerce.ecommercestore.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CartItemRepository cartItemRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public ProductDetailResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productRequest.getCategoryId()));

        Product product = productMapper.toProduct(productRequest);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toProductDetailResponse(savedProduct);
    }

    @Override
    public List<ProductSummaryResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return productMapper.toProductDetailResponse(product);
    }

    @Override
    public ProductDetailResponse updateProduct(String productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productRequest.getCategoryId()));

        // Update fields
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setImageUrl(productRequest.getImageUrl());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setCategory(category);
        existingProduct.setAttributes(productRequest.getAttributes());

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toProductDetailResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        List<CartItem> cartItems = cartItemRepository.findAllByProduct(product);
        cartItemRepository.deleteAll(cartItems);

        productRepository.delete(product);
    }

    @Override
    public ProductResponse mapToDTO(Product product) {
        return productMapper.toProductResponse(product);
    }
}
