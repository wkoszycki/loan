package com.poccofinance.loan.repository;

import com.poccofinance.loan.exception.ResourceConflictedException;
import org.joda.time.LocalDateTime;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.repository.CrudRepository;

/**
 * When concurrent updates occurs, this strategy will fail them by  throwing exception.
 * In future based on resource we could implement RetryUpdateStrategy,
 * which would try to obtain fresh data and perform update  again.
 *
 * @param <T> updatableResource
 */
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
