package com.poccofinance.loan.exception;

import lombok.ToString;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

@ToString
public class InvalidInputException extends RuntimeException{

    private final Set<ConstraintViolation<Serializable>> constraintViolations;

    public InvalidInputException(Set<ConstraintViolation<Serializable>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    Set<ConstraintViolation<Serializable>> getConstraintViolations() {
        return constraintViolations;
    }
}
