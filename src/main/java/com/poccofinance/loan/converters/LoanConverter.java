package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import org.springframework.stereotype.Service;

public interface LoanConverter {

    Loan convert(LoanApplianceDTO dto);

    LoanApplianceResultDTO convert(Loan loan);

    Loan convert(LoanExtensionDTO dto);

    LoanExtensionResultDTO extendLoan(Loan loan);
}