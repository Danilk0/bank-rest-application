package com.example.bankcards.dto.user;

import com.example.bankcards.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UserResponse {
    Long id;
    String email;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
}
