package com.poccofinance.loan.exception;

public class ResourceConflictedException extends RuntimeException {

    public ResourceConflictedException(Class clazz, String resourceId) {
        super("Resource can't be updated due to concurrent update type: " + clazz.getSimpleName() + " and id: " + resourceId);
    }
}
