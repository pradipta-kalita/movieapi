package com.pradiptakalita.validation;

import com.pradiptakalita.validation.validator.ReleaseYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseYearValidator.class)
public @interface ValidReleaseYear {
    String message() default "Invalid release year.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
