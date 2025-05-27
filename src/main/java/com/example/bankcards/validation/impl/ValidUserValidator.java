package com.example.bankcards.validation.impl;

import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.validation.ValidUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidUserValidator implements ConstraintValidator<ValidUser, Long> {
    private final UserRepository userRepository;


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return userRepository.findById(value).isPresent();
    }
}
