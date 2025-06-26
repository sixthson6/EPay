package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.EcommerceAPIException;
import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.model.CartItem;
import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.model.User;
import com.ecommerce.ecommercestore.payload.cart.AddItemRequest;
import com.ecommerce.ecommercestore.payload.cart.CartItemResponse;
import com.ecommerce.ecommercestore.payload.cart.CartResponse;
import com.ecommerce.ecommercestore.repository.CartItemRepository;
import com.ecommerce.ecommercestore.repository.CartRepository;
import com.ecommerce.ecommercestore.repository.ProductRepository;
import com.ecommerce.ecommercestore.repository.UserRepository;
import com.ecommerce.ecommercestore.service.CartService;
import com.ecommerce.ecommercestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           UserRepository userRepository, ProductRepository productRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public Cart getCartByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public CartResponse addProductToCart(Long userId, AddItemRequest addItemRequest) {
        Cart cart = getCartByUser(userId);

        Product product = productRepository.findById(addItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", addItemRequest.getProductId().toString()));

        if (product.getStockQuantity() == 0) {
            throw new EcommerceAPIException(HttpStatus.BAD_REQUEST, "Product '" + product.getName() + "' is out of stock.");
        }

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + addItemRequest.getQuantity();

            if (newQuantity > product.getStockQuantity()) {
                throw new EcommerceAPIException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for product '" + product.getName() + "'. Available: " + product.getStockQuantity());
            }
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        } else {
            if (addItemRequest.getQuantity() > product.getStockQuantity()) {
                throw new EcommerceAPIException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for product '" + product.getName() + "'. Available: " + product.getStockQuantity());
            }
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(addItemRequest.getQuantity())
                    .build();
            cartItemRepository.save(newCartItem);
            cart.getCartItems().add(newCartItem);
        }
        return mapToDTO(cart);
    }

    @Override
    @Transactional
    public CartResponse updateProductQuantityInCart(Long userId, AddItemRequest addItemRequest) {
        Cart cart = getCartByUser(userId);

        Product product = productRepository.findById(addItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", addItemRequest.getProductId().toString()));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "productId", addItemRequest.getProductId().toString()));

        int newQuantity = addItemRequest.getQuantity();

        if (newQuantity <= 0) {
            cartItemRepository.delete(cartItem);
            cart.getCartItems().remove(cartItem);
        } else {
            if (newQuantity > product.getStockQuantity()) {
                throw new EcommerceAPIException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for product '" + product.getName() + "'. Available: " + product.getStockQuantity());
            }
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }
        return mapToDTO(cart);
    }

    @Override
    @Transactional
    public CartResponse removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "productId", productId.toString()));

        cartItemRepository.delete(cartItem);
        cart.getCartItems().remove(cartItem);
        return mapToDTO(cart);
    }

    @Override
    @Transactional
    public CartResponse clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        return mapToDTO(cart);
    }

    @Override
    public CartResponse mapToDTO(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setUserId(cart.getUser().getId());

        List<CartItemResponse> itemResponses = cart.getCartItems().stream()
                .map(item -> {
                    CartItemResponse itemResponse = new CartItemResponse();
                    itemResponse.setId(item.getId());
                    itemResponse.setProduct(productService.mapToDTO(item.getProduct()));
                    itemResponse.setQuantity(item.getQuantity());
                    itemResponse.setSubtotal(item.getQuantity() * 10.0);
                    return itemResponse;
                }).collect(Collectors.toList());

        cartResponse.setItems(itemResponses);

        double total = itemResponses.stream()
                .mapToDouble(CartItemResponse::getSubtotal)
                .sum();
        cartResponse.setTotal(total);

        return cartResponse;
    }
}