package com.example.bankcards.validation;

import com.example.bankcards.validation.impl.ValidUserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidUserValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUser {

    String message() default "No such user exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
