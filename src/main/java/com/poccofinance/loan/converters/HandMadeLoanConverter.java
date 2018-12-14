package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import lombok.NonNull;
import org.joda.time.LocalDateTime;

import java.util.Optional;

/**
 * Manually implemented converter, can be swapped by later on with mapStruct or other libraries.
 */
public class HandMadeLoanConverter implements LoanConverter {

    @Override
    public Loan.LoanBuilder convert(LoanApplianceDTO dto) {
        return Optional.ofNullable(dto).map(d -> Loan.builder()
            .amount(d.getAmount())
            .term(d.getTerm())
            .requestedDate(LocalDateTime.now()))
            .orElseGet(Loan::builder);
    }

    @Override
    public LoanApplianceResultDTO convertLoan(Loan loan) {
        return Optional.ofNullable(loan).map(l -> LoanApplianceResultDTO.builder()
            .loanId(l.getLoanId())
            .term(l.getTerm())
            .amount(l.getAmount())
            .startDate(l.getRequestedDate())
            .build())
            .orElseGet(LoanApplianceResultDTO::new);

    }

    @Override
    public Loan.LoanBuilder shallowCopy(Loan loan) {
        return Optional.ofNullable(loan).map(l ->
            Loan.builder()
                .loanId(l.getLoanId())
                .amount(l.getAmount())
                .term(l.getTerm())
                .principal(l.getPrincipal())
                .requestedDate(l.getRequestedDate())
                .dueDate(l.getDueDate()))
            .orElseGet(Loan::builder);

    }

    @Override
    public LoanExtensionResultDTO convertExtendedLoan(Loan loan) {
        return Optional.ofNullable(loan).map(l ->
            LoanExtensionResultDTO.builder()
                .id(l.getId())
                .loanId(l.getLoanId())
                .dueDate(l.getDueDate())
                .build())
            .orElseGet(LoanExtensionResultDTO::new);
    }
}
