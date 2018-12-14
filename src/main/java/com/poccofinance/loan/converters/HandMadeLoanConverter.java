package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import org.joda.time.LocalDateTime;

/**
 * Manually implemented converter, can be swapped by later on with mapStruct or other libraries.
 */
public class HandMadeLoanConverter implements LoanConverter {

    @Override
    public Loan convert(LoanApplianceDTO dto) {
        Loan loan = new Loan();
        loan.setAmount(dto.getAmount());
        loan.setTerm(dto.getTerm());
        loan.setRequestedDate(LocalDateTime.now());
        return loan;
    }

    @Override
    public LoanApplianceResultDTO convert(Loan loan) {
        return LoanApplianceResultDTO.builder()
            .loanId(loan.getLoanId())
            .term(loan.getTerm())
            .amount(loan.getAmount())
            .startDate(loan.getRequestedDate())
            .build();
    }

    @Override
    public Loan shallowCopy(Loan loan) {
        final Loan loanCopy = new Loan();
        loanCopy.setLoanId(loan.getLoanId());
        loanCopy.setAmount(loan.getAmount());
        loanCopy.setTerm(loan.getTerm());
        loanCopy.setPrincipal(loan.getPrincipal());
        loanCopy.setRequestedDate(loan.getRequestedDate());
        loanCopy.setDueDate(loan.getDueDate());
        return loanCopy;
    }

    @Override
    public LoanExtensionResultDTO convertExtendedLoan(Loan loan) {
        return LoanExtensionResultDTO.builder()
            .id(loan.getId())
            .loanId(loan.getLoanId())
            .dueDate(loan.getDueDate())
            .build();
    }
}
