package com.example.bankcards.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {

    @Size(min = 5, max = 255, message = "The email must be between 5 and 50 characters long")
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Email should be in the format user@example.com")
    String email;

    @Size(min = 5, max = 255, message = "The password must be between 5 and 255 characters long")
    @NotBlank(message = "The password cannot be empty")
    String password;
}
