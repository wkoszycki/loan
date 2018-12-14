package com.poccofinance.loan.repository;

import com.poccofinance.loan.exception.ResourceConflictedException;
import org.joda.time.LocalDateTime;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.repository.CrudRepository;

public class ConflictingUpdateStrategy<T extends UpdatableResource> implements ResourceUpdateStrategy<T> {

    private final CrudRepository<T, Long> resourceRepository;

    public ConflictingUpdateStrategy(CrudRepository<T, Long> resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public T updateResource(T entity) {
        try {
            entity.setLastUpdate(LocalDateTime.now());
            return resourceRepository.save(entity);
        } catch (OptimisticLockingFailureException ex) {
            throw new ResourceConflictedException(entity.getClass(), entity.getId().toString());
        }
    }

}
