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
@Constraint(validatedBy = CorrectTermPeriodValidator.class)
@Documented
public @interface CorrectTermPeriod {

  String message() default "{com.poccofinance.loan.validators.correct-term-period}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};


}