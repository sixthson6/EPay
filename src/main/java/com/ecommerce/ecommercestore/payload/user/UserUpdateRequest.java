package com.ecommerce.ecommercestore.payload.user;

import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String username;
    @Email(message = "Invalid email format")
    private String email;
}