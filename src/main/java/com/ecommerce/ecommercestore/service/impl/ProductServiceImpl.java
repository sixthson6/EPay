package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.EcommerceAPIException;
import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.model.enums.CategoryName; // Import CategoryName
import com.ecommerce.ecommercestore.payload.product.ProductRequest;
import com.ecommerce.ecommercestore.payload.product.ProductResponse;
import com.ecommerce.ecommercestore.repository.CategoryRepository;
import com.ecommerce.ecommercestore.repository.ProductRepository;
import com.ecommerce.ecommercestore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findByName(productRequest.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", productRequest.getCategoryName().name()));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(category); // Set the associated Category entity

        Product newProduct = productRepository.save(product);
        return mapToDTO(newProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));
        return mapToDTO(product);
    }

    @Override
    public List<ProductResponse> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir, String nameFilter) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products;
        if (nameFilter != null && !nameFilter.trim().isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(nameFilter, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

        List<Product> listOfProducts = products.getContent();
        return listOfProducts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategoryName(CategoryName categoryName) {
        // Find the Category entity by its enum name first
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName.name()));

        List<Product> products = productRepository.findByCategoryId(category.getId()); // Use category ID for lookup
        return products.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

        // Find category by name (enum)
        Category category = categoryRepository.findByName(productRequest.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", productRequest.getCategoryName().name()));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(category); // Update the category if changed

        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));
        productRepository.delete(product);
    }

    @Override
    public ProductResponse mapToDTO(Product product) {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        productResponse.setCategoryName(product.getCategory().getName());
        return productResponse;
    }

    @Override
    public Product mapToEntity(ProductRequest productRequest) {
          Product product = modelMapper.map(productRequest, Product.class);
        return product;
    }
}

