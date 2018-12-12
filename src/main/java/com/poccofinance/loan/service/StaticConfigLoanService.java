package com.poccofinance.loan.service;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.LoanRepository;
import com.poccofinance.loan.converters.LoanConverter;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Service
public class StaticConfigLoanService implements LoanService{


    private final Double loanPrincipal;
    private final Integer extendTermDays;
    private final LoanRepository loanRepository;
    private final LoanConverter loanConverter;
    private final Validator validator;

    @Autowired
    public StaticConfigLoanService(
        @Value("${com.poccofinance.loan.principal}") Double loanPrincipal,
        @Value("${com.poccofinance.loan.extend-term-days}") Integer extendTermDays,
        LoanRepository loanRepository,
        LoanConverter loanConverter,
        Validator validator) {

        this.loanPrincipal = loanPrincipal;
        this.extendTermDays = extendTermDays;
        this.loanRepository = loanRepository;
        this.loanConverter = loanConverter;
        this.validator = validator;
    }

    @Override
    public LoanApplianceResultDTO applyForLoan(LoanApplianceDTO loanApplianceDTO) throws InvalidInputException {
        final Set<ConstraintViolation<Serializable>> constraintViolations = validate(loanApplianceDTO);
        if (!constraintViolations.isEmpty()) {
            throw new InvalidInputException(constraintViolations);
        }
        final Loan loan = loanConverter.convert(loanApplianceDTO);
        loan.setLoanId(UUID.randomUUID());
        loan.setPrincipal(loanPrincipal);
        loan.setDueDate(loan.getRequestedDate().plusDays(loan.getTerm()));
        final Loan savedLoan = loanRepository.save(loan);
        return loanConverter.convert(savedLoan);
    }

    @Override
    public LoanExtensionResultDTO extendLoan(LoanExtensionDTO loanExtensionDTO) {
        throw new UnsupportedOperationException("not implemented");
    }

    private Set<ConstraintViolation<Serializable>> validate(Serializable objectToValidate) {
        return validator.validate(objectToValidate);
    }
}