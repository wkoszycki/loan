package com.poccofinance.loan.repository;

public interface ResourceUpdateStrategy<T> {

     T updateResource(T entity);
}
