package com.poccofinance.loan.repository;

import com.poccofinance.loan.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RestResource(exported = false)
@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>{

    Iterable<Loan> findByLoanIdOrderByRequestedDate(UUID loanId);
}