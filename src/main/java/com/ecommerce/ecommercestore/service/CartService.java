package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.Cart;
import com.ecommerce.ecommercestore.payload.cart.AddItemRequest;
import com.ecommerce.ecommercestore.payload.cart.CartResponse;

public interface CartService {
    Cart getCartByUser(String userId);
    CartResponse addProductToCart(String userId, AddItemRequest addItemRequest);
    CartResponse updateProductQuantityInCart(String userId, AddItemRequest addItemRequest);
    CartResponse removeProductFromCart(String userId, String productId);
    CartResponse clearCart(String userId);
    CartResponse mapToDTO(Cart cart);
}