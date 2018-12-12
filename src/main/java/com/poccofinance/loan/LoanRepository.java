package com.poccofinance.loan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>{
//    @Query("select u from User u where u.status != :status")
    Iterable<Loan> findByLoanId(UUID loanId);
}