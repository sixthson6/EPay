package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.User;
import com.ecommerce.ecommercestore.payload.user.UserResponse;
import com.ecommerce.ecommercestore.payload.user.UserUpdateRequest;

public interface UserService {
    User createUser(User user);
    UserResponse getUserById(Long userId);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest);
    void deleteUser(Long userId);
    Boolean existsByEmail(String email);
    UserResponse mapToDTO(User user);
    User mapToEntity(UserResponse userResponse);
}