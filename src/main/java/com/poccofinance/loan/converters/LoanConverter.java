package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;

/**
 * Interface for converting entity <-> dto
 */
public interface LoanConverter {

    Loan convert(LoanApplianceDTO dto);

    LoanApplianceResultDTO convert(Loan loan);

    Loan shallowCopy(Loan loan);

    LoanExtensionResultDTO convertExtendedLoan(Loan loan);
}