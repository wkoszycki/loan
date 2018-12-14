package com.poccofinance.loan.validators;

import com.poccofinance.loan.TypeSafeConfig;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;


public class CorrectAmountValidator implements ConstraintValidator<CorrectAmount, Long> {

    @Autowired
    private TypeSafeConfig config;
    /**
     * Validating function for amount.
     *
     * @param value   amount value
     * @param context surrounding context
     * @return true for non empty value and non max between restricted hours and basic ranges (min,max) satisfied.
     */
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && !isMaxAmountBetweenHours(value, LocalDateTime.now()) && value <= config.getLoanMaxAmount() && value >= config.getLoanMinAmount();
    }

    private boolean isMaxAmountBetweenHours(Long value, LocalDateTime now) {
        return Objects.equals(value, config.getLoanMaxAmount())
            && now.isAfter(now.withHourOfDay(config.getRejectStartHour()).withMinuteOfHour(0))
            && now.isBefore(now.withHourOfDay(config.getRejectStopHour()).withMinuteOfHour(0));
    }

}