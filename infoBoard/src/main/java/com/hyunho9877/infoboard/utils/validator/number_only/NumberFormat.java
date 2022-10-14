package com.hyunho9877.infoboard.utils.validator.number_only;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NumberFormatValidator.class)
public @interface NumberFormat {
    String message() default "This fields cannot contain non-number value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
