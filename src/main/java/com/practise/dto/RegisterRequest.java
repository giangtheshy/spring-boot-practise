package com.practise.dto;

import com.practise.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
}

