package com.poccofinance.loan.service;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.exception.ResourceConflictedException;
import com.poccofinance.loan.exception.ResourceNotFoundException;
import com.poccofinance.loan.repository.LoanRepository;
import com.poccofinance.loan.PropertyInjectedUtil;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"com.poccofinance.loan.principal=5"})
public class StaticConfigLoanServiceTest extends PropertyInjectedUtil {


    @Autowired
    private StaticConfigLoanService loanService;


    @Before
    public void setUp() throws Exception {
        setFixedTime(1544604544700L);
    }

    @Test
    public void whenApplyingLoanWithCorrectData_ShouldCreateLoan() throws Exception {

        LoanApplianceDTO dto = new LoanApplianceDTO(minTerm, minAmount);

        final LoanApplianceResultDTO result = loanService.applyForLoan(dto);
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertNotNull("it should generate id", result.getLoanId());

        final Loan loan = loanRepository.findByLoanIdOrderByRequestedDate(result.getLoanId()).iterator().next();

        assertEquals(loanPrincipal, loan.getPrincipal());
        assertEquals(LocalDateTime.now(), loan.getRequestedDate());
        assertEquals(LocalDateTime.now().plusDays(loan.getTerm()), loan.getDueDate());

    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanWithNullValues_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO());
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowMinTerm_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(minTerm - 1, minAmount));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowMinAmount_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(minTerm, minAmount - 1));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowAboveMaxTerm_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(maxTerm + 1, minAmount));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanAboveMaxAmount_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(minTerm, maxAmount + 1));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBetweenRestrictedHours_ShouldFailOnValidation() throws Exception {
        setFixedTime(LocalDateTime.now().withHourOfDay(minHour).plusMinutes(1));

        loanService.applyForLoan(new LoanApplianceDTO(minTerm, maxAmount));
    }

    @Test
    public void whenApplyingLoanBetweenRestrictedHoursWithMinAmount_ShouldCreateLoan() throws Exception {
        final LocalDateTime now = setFixedTime(LocalDateTime.now().withHourOfDay(minHour).plusMinutes(1));

        LoanApplianceDTO dto = new LoanApplianceDTO(minTerm, minAmount);

        final LoanApplianceResultDTO result = loanService.applyForLoan(dto);
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertNotNull("it should generate id", result.getLoanId());

        final Loan loan = loanRepository.findByLoanIdOrderByRequestedDate(result.getLoanId()).iterator().next();

        assertEquals(loanPrincipal, loan.getPrincipal());
        assertEquals(LocalDateTime.now(), loan.getRequestedDate());
        assertEquals(now.plusDays(minTerm), loan.getDueDate());
    }

    //----------------------------------------- Loan extend  tests -----------------------------------------

    @Test
    public void whenExtendingLoan_ShouldUpdateExistingLoan() throws Exception {
        final Loan initialLoan = prepareSampleLoan();
        final LoanExtensionResultDTO result = loanService.extendLoan(new LoanExtensionDTO(initialLoan.getLoanId()));

        assertEquals(initialLoan.getLoanId(), result.getLoanId());
        final LocalDateTime expectedDueDate = initialLoan.getDueDate().plusDays(loanExtensionTermDays);
        assertEquals(expectedDueDate, result.getDueDate());

        final Loan updatedLoan = loanRepository.findById(result.getId()).get();
        assertEquals(initialLoan.getLoanId(), updatedLoan.getLoanId());
        assertEquals(expectedDueDate, updatedLoan.getDueDate());
        assertEquals(LocalDateTime.now(), updatedLoan.getRequestedDate());
        assertEquals(initialLoan.getAmount(), updatedLoan.getAmount());
        assertEquals(initialLoan.getPrincipal(), updatedLoan.getPrincipal());
        assertEquals(loanExtensionTermDays, updatedLoan.getTerm());
        //check if old record was updated
        final Loan previousLoan = loanRepository.findById(initialLoan.getId()).get();
        assertEquals(LocalDateTime.now(), previousLoan.getLastUpdate());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenExtendingNonExistingLoan_ShouldThrowException() throws Exception {
        loanService.extendLoan(new LoanExtensionDTO(UUID.randomUUID()));
    }




}