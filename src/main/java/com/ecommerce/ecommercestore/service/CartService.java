package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.payload.cart.AddItemRequest;
import com.ecommerce.ecommercestore.payload.cart.CartResponse;

public interface CartService {
    Cart getCartByUser(Long userId);
    CartResponse addProductToCart(Long userId, AddItemRequest addItemRequest);
    CartResponse updateProductQuantityInCart(Long userId, AddItemRequest addItemRequest);
    CartResponse removeProductFromCart(Long userId, Long productId);
    CartResponse clearCart(Long userId);
    CartResponse mapToDTO(Cart cart);
}