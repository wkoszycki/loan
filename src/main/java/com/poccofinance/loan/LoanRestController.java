package com.poccofinance.loan;

import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import com.poccofinance.loan.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

@Slf4j
@RestController
public class LoanRestController implements LoanController {

    private final LoanService loanService;

    @Autowired
    LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loans")
    @ResponseBody
    public ResponseEntity<Serializable> create(@RequestBody @Valid LoanApplianceDTO loanApplianceDTO) {
        return new ResponseEntity<>(applyForLoan(loanApplianceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/loans")
    @ResponseBody
    public ResponseEntity<Serializable> update(@RequestBody @Valid LoanExtensionDTO loanExtensionDTO) {
        return new ResponseEntity<>(extendLoan(loanExtensionDTO), HttpStatus.OK);
    }

    @Override
    public LoanApplianceResultDTO applyForLoan(LoanApplianceDTO loanApplianceDTO) throws InvalidInputException {
        return loanService.applyForLoan(loanApplianceDTO);
    }

    @Override
    public LoanExtensionResultDTO extendLoan(LoanExtensionDTO loanExtensionDTO) throws InvalidInputException {
        return loanService.extendLoan(loanExtensionDTO);
    }
}