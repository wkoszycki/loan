package com.poccofinance.loan.service;

import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public interface LoanService {

    /**
     * Creates new resource - loan in particular.
     *
     * @param loanApplianceDTO object containing all necessary parameters for loan appliance.
     * @return information about issued loan.
     */
    LoanApplianceResultDTO applyForLoan(LoanApplianceDTO loanApplianceDTO) throws InvalidInputException;

    /**
     * Updates existing resource by extending existing loan with preconfigured term.
     *
     * @param loanExtensionDTO object containing all necessary parameters for loan extension.
     * @return information about extended loan.
     */
    LoanExtensionResultDTO extendLoan(LoanExtensionDTO loanExtensionDTO) throws InvalidInputException;
}