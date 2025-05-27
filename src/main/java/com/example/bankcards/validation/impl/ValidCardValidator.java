package com.example.bankcards.validation.impl;

import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.validation.ValidCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidCardValidator implements ConstraintValidator<ValidCard, Long> {
    private final CardRepository cardRepository;


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return cardRepository.findById(value).isPresent();
    }
}
