package com.poccofinance.loan.exception;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class clazz, String resourceId) {
        super("Resource not found for type: " + clazz.getSimpleName() + " and id: " + resourceId);
    }
}
