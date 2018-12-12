package com.poccofinance.loan.validators;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Configuration of this class is static however any time we could inject Repository class to obtain
 * config from persistent store. It is just to demonstrate that @Min @Max value on object level wouldn't be sufficient.
 */
public class CorrectTermPeriodValidator implements ConstraintValidator<CorrectTermPeriod, Integer> {

    @Value("${com.poccofinance.loan.validators.correct-term-period.min-term}")
    private Integer minTerm;

    @Value("${com.poccofinance.loan.validators.correct-term-period.max-term}")
    private Integer maxTerm;

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value <= maxTerm && value >= minTerm;
    }

}