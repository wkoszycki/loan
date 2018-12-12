package com.poccofinance.loan.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CorrectAmountValidator.class)
@Documented
public @interface CorrectAmount {

    String message() default "{com.poccofinance.loan.validators.correct-amount}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class type() default Long.class;

}