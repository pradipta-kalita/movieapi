package com.pradiptakalita.validation.validator;

import com.pradiptakalita.validation.ValidReleaseYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Integer> {

    @Override
    public boolean isValid(Integer releaseYear, ConstraintValidatorContext context) {
        if (releaseYear == null) {
            return false;
        }
        int currentYear = LocalDate.now().getYear();
        return releaseYear >= 1888 && releaseYear <= currentYear;
    }
}
