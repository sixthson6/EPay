// src/main/java/com/ecommerce/ecommercestore/service/impl/UserServiceImpl.java
package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.User;
import com.ecommerce.ecommercestore.model.enums.Role;
import com.ecommerce.ecommercestore.payload.user.UserResponse;
import com.ecommerce.ecommercestore.payload.user.UserUpdateRequest;
import com.ecommerce.ecommercestore.repository.UserRepository;
import com.ecommerce.ecommercestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        Optional.ofNullable(userUpdateRequest.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userUpdateRequest.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userUpdateRequest.getEmail()).ifPresent(user::setEmail);

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        userRepository.delete(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse mapToDTO(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public User mapToEntity(UserResponse userResponse) {
        return modelMapper.map(userResponse, User.class);
    }
}
