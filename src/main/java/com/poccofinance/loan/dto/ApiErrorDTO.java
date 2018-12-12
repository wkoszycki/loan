package com.poccofinance.loan.dto;

import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
public class ApiErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<ConstraintViolation<Serializable>> constraintViolations;

    public ApiErrorDTO(Set<ConstraintViolation<Serializable>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    public Set<ConstraintViolation<Serializable>> getConstraintViolations() {
        return constraintViolations;
    }
}
