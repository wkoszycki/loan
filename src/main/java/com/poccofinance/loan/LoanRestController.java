package com.poccofinance.loan;

import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
class LoanRestController implements LoanController {

    private final LoanService loanService;

    @Autowired
    LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanApplianceResultDTO> create(@RequestBody @Valid LoanApplianceDTO loanApplianceDTO) {
        return new ResponseEntity<>(applyForLoan(loanApplianceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/loans")
    public ResponseEntity<LoanExtensionResultDTO> update(@RequestBody @Valid LoanExtensionDTO loanExtensionDTO) {
        return new ResponseEntity<>(extendLoan(loanExtensionDTO), HttpStatus.OK);
    }

    @Override
    public LoanApplianceResultDTO applyForLoan(LoanApplianceDTO loanApplianceDTO) {
        return loanService.applyForLoan(loanApplianceDTO);
    }

    @Override
    public LoanExtensionResultDTO extendLoan(LoanExtensionDTO loanExtensionDTO) {
        return loanService.extendLoan(loanExtensionDTO);
    }
}