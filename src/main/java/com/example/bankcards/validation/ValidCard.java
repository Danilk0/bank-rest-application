package com.example.bankcards.validation;

import com.example.bankcards.validation.impl.ValidCardValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCardValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCard {

    String message() default "No such card exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
