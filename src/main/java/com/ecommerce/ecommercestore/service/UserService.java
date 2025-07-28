package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.User;
import com.ecommerce.ecommercestore.payload.user.UserResponse;
import com.ecommerce.ecommercestore.payload.user.UserUpdateRequest;

public interface UserService {
    void createUser(User user);
    UserResponse getUserById(String userId);
    void createAdminUser(User user);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest);
    void deleteUser(String userId);
    Boolean existsByEmail(String email);
}
