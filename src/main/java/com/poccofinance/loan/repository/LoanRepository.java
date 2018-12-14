package com.poccofinance.loan.repository;

import com.poccofinance.loan.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>{

    Iterable<Loan> findByLoanIdOrderByRequestedDate(UUID loanId);
}