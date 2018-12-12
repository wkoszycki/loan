package com.poccofinance.loan;

import com.poccofinance.loan.dto.*;
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
        try {
            return new ResponseEntity<>(applyForLoan(loanApplianceDTO), HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ApiErrorDTO(e.getConstraintViolations()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/loans")
    @ResponseBody
    public ResponseEntity<Serializable> update(@RequestBody @Valid LoanExtensionDTO loanExtensionDTO) {
        try {
            return new ResponseEntity<>(extendLoan(loanExtensionDTO), HttpStatus.OK);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ApiErrorDTO(e.getConstraintViolations()), HttpStatus.BAD_REQUEST);
        }
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