package com.hyunho9877.freeboard.utils.validator.number_only;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumberFormatValidator {

    private static final Pattern pattern = Pattern.compile("[a-z|A-Z]");

    public static boolean isValid(String value) {
        if(value == null) return true;
        return !pattern.matcher(value).matches();
    }
}
