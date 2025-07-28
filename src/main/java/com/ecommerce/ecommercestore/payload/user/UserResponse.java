package com.ecommerce.ecommercestore.payload.user;

import com.ecommerce.ecommercestore.model.enums.Role;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
