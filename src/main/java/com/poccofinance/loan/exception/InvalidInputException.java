package com.poccofinance.loan.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@ToString
public class InvalidInputException extends RuntimeException{

    private final Set<ConstraintViolation<Serializable>> constraintViolations;

    public InvalidInputException(Set<ConstraintViolation<Serializable>> constraintViolations) {
        super("Invalid input data.");
        this.constraintViolations = constraintViolations;
    }

}
