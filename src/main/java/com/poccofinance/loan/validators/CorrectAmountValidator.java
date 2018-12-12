package com.poccofinance.loan.validators;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;


public class CorrectAmountValidator implements ConstraintValidator<CorrectAmount, Long> {

    @Value("${com.poccofinance.loan.validators.correct-amount.min-amount}")
    private Long minAmount;

    @Value("${com.poccofinance.loan.validators.correct-amount.max-amount}")
    private Long maxAmount;

    @Value("${com.poccofinance.loan.validators.correct-amount.min-hour}")
    private Integer minHour;

    @Value("${com.poccofinance.loan.validators.correct-amount.max-hour}")
    private Integer maxHour;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && !isMaxAmountBetweenHours(value, LocalDateTime.now()) && value <= maxAmount && value >= minAmount;
    }

    private boolean isMaxAmountBetweenHours(Long value, LocalDateTime now) {
        return Objects.equals(value, maxAmount)
            && now.isAfter(now.withHourOfDay(minHour).withMinuteOfHour(0))
            && now.isBefore(now.withHourOfDay(maxHour).withMinuteOfHour(0));
    }


}