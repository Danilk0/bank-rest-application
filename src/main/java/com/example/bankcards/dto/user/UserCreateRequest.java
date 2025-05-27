package com.example.bankcards.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserCreateRequest {

    @Size(min = 5, max = 255, message = "The email must be between 5 and 255 characters long")
    @NotBlank(message = "The Email cannot be empty")
    @Email(message = "Email should be in the format user@example.com")
    String email;

    @Size(min = 5, max = 255, message = "The password must be between 5 and 255 characters long")
    @NotBlank(message = "The password cannot be empty")
    String password;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 5, max = 255, message = "The firstname must be between 5 and 255 characters long")
    String firstname;

    @Size(min = 5, max = 255, message = "The lastname must be between 5 and 255 characters long")
    String lastname;

}
