package com.wk6.assignment4.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        // Only compare the date part, not the time
        return value.toLocalDate().isEqual(LocalDate.now()) || 
               value.toLocalDate().isAfter(LocalDate.now());
    }
}