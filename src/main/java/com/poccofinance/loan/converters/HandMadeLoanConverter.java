package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import org.joda.time.LocalDateTime;


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
    public Loan convert(LoanExtensionDTO dto) {
        return null;
    }

    @Override
    public LoanExtensionResultDTO extendLoan(Loan loan) {
        return null;
    }
}
