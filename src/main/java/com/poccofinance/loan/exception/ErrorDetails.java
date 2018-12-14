package com.poccofinance.loan.exception;

import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
public class ErrorDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<ConstraintViolation<Serializable>> constraintViolations;
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(Set<ConstraintViolation<Serializable>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Set<ConstraintViolation<Serializable>> getConstraintViolations() {
        return constraintViolations;
    }
}
