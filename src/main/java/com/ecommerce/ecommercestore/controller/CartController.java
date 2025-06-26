package com.ecommerce.ecommercestore.controller;

import com.ecommerce.ecommercestore.payload.cart.AddItemRequest;
import com.ecommerce.ecommercestore.payload.cart.CartResponse;
import com.ecommerce.ecommercestore.service.CartService;
import com.ecommerce.ecommercestore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    private Long getCurrentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email).getId();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> getCart(){
        Long userId = getCurrentAuthenticatedUserId();
        CartResponse cartResponse = cartService.mapToDTO(cartService.getCartByUser(userId));
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> addProductToCart(@Valid @RequestBody AddItemRequest addItemRequest){
        Long userId = getCurrentAuthenticatedUserId();
        CartResponse cartResponse = cartService.addProductToCart(userId, addItemRequest);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> updateProductQuantityInCart(@Valid @RequestBody AddItemRequest updateItemRequest){
        Long userId = getCurrentAuthenticatedUserId();
        CartResponse cartResponse = cartService.updateProductQuantityInCart(userId, updateItemRequest);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> removeProductFromCart(@PathVariable("productId") Long productId){
        Long userId = getCurrentAuthenticatedUserId();
        CartResponse cartResponse = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> clearCart(){
        Long userId = getCurrentAuthenticatedUserId();
        CartResponse cartResponse = cartService.clearCart(userId);
        return ResponseEntity.ok(cartResponse);
    }
}
