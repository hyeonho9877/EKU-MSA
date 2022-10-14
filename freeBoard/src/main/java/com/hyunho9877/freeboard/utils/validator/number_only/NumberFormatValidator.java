package com.hyunho9877.freeboard.utils.validator.number_only;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumberFormatValidator implements ConstraintValidator<NumberFormat, String> {

    private final Pattern pattern = Pattern.compile("[a-z|A-Z]");

    @Override
    public void initialize(NumberFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return !pattern.matcher(value).matches();
    }
}
