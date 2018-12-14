package com.poccofinance.loan.repository;

import org.joda.time.LocalDateTime;

public interface UpdatableResource {

    Long getId();

    void setLastUpdate(LocalDateTime lastUpdate);
}
