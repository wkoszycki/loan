package com.poccofinance.loan.exception;

import lombok.ToString;

@ToString
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class clazz, String resourceId) {
        super("Resource not found for type: " + clazz.getSimpleName() + " and id: " + resourceId);
    }
}
