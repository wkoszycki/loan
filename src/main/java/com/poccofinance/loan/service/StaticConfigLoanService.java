package com.poccofinance.loan.service;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.converters.LoanConverter;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import com.poccofinance.loan.exception.ResourceNotFoundException;
import com.poccofinance.loan.repository.LoanRepository;
import com.poccofinance.loan.repository.ResourceUpdateStrategy;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * LoanService implementation with static values configured such as: fixedExtendTermDays, fixedLoanPrincipal
 */
@Service
public class StaticConfigLoanService implements LoanService{

    private final Double fixedLoanPrincipal;
    private final Integer fixedExtendTermDays;
    private final LoanRepository loanRepository;
    private final LoanConverter loanConverter;
    private final Validator validator;
    private final ResourceUpdateStrategy<Loan> resourceUpdateStrategy;

    @Autowired
    public StaticConfigLoanService(
        @Value("${com.poccofinance.loan.principal}") Double fixedLoanPrincipal,
        @Value("${com.poccofinance.loan.extend-term-days}") Integer fixedExtendTermDays,
        LoanRepository loanRepository,
        LoanConverter loanConverter,
        Validator validator,
        ResourceUpdateStrategy<Loan> resourceUpdateStrategy) {

        this.fixedLoanPrincipal = fixedLoanPrincipal;
        this.fixedExtendTermDays = fixedExtendTermDays;
        this.loanRepository = loanRepository;
        this.loanConverter = loanConverter;
        this.validator = validator;
        this.resourceUpdateStrategy = resourceUpdateStrategy;
    }

    @Override
    public LoanApplianceResultDTO applyForLoan(LoanApplianceDTO loanApplianceDTO) throws InvalidInputException {
        checkConstraints(loanApplianceDTO);
        final Loan.LoanBuilder loan = loanConverter.convert(loanApplianceDTO)
            .loanId(UUID.randomUUID())
            .principal(fixedLoanPrincipal)
            .dueDate(LocalDateTime.now().plusDays(loanApplianceDTO.getTerm()));

        final Loan savedLoan = loanRepository.save(loan.build());
        return loanConverter.convertLoan(savedLoan);
    }

    @Override
    public LoanExtensionResultDTO extendLoan(LoanExtensionDTO loanExtensionDTO) throws InvalidInputException {
        checkConstraints(loanExtensionDTO);
        final Iterator<Loan> loanIterator = loanRepository.findByLoanIdOrderByRequestedDate(loanExtensionDTO.getLoanId()).iterator();
        if (!loanIterator.hasNext()) {
            throw new ResourceNotFoundException(Loan.class, loanExtensionDTO.getLoanId().toString());
        }
        final Loan latestLoan = loanIterator.next();
        final Loan.LoanBuilder loanCopy = loanConverter.shallowCopy(latestLoan)
            .term(fixedExtendTermDays)
            .dueDate(latestLoan.getDueDate().plusDays(fixedExtendTermDays));

        resourceUpdateStrategy.updateResource(latestLoan);

        final Loan extendedLoan = loanRepository.save(loanCopy.build());
        return loanConverter.convertExtendedLoan(extendedLoan);
    }

    private void checkConstraints(Serializable objectToValidate) throws InvalidInputException {
        final Set<ConstraintViolation<Serializable>> constraintViolations = validator.validate(objectToValidate);
        if (!constraintViolations.isEmpty()) {
            throw new InvalidInputException(constraintViolations);
        }
    }

}