package com.poccofinance.loan.validators;

import com.poccofinance.loan.TypeSafeConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Configuration of this class is static however any time we could inject Repository class to obtain
 * config from persistent store. It is just to demonstrate that @Min @Max value on object level wouldn't be sufficient.
 */
public class CorrectTermPeriodValidator implements ConstraintValidator<CorrectTermPeriod, Integer> {

    @Autowired
    private TypeSafeConfig config;

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value <= config.getLoanMaxTermDays() && value >= config.getLoanMinTermDays();
    }

}