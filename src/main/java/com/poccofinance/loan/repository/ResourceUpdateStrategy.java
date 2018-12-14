package com.poccofinance.loan.repository;

public interface ResourceUpdateStrategy<T> {

    /**
     * Will update specific resource, and return fresh object.
     *
     * @param entity entity object  to persist.
     * @return fresh object fromn db.
     */
    T updateResource(T entity);
}
