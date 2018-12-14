package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;

/**
 * Interface for converting entity <-> dto
 */
public interface LoanConverter {

    Loan.LoanBuilder convert(LoanApplianceDTO dto);

    LoanApplianceResultDTO convertLoan(Loan loan);

    Loan.LoanBuilder shallowCopy(Loan loan);

    LoanExtensionResultDTO convertExtendedLoan(Loan loan);
}