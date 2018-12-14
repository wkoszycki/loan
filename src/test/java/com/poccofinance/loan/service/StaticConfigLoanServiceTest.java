package com.poccofinance.loan.service;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.PropertyInjectedUtil;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import com.poccofinance.loan.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.security.InvalidAlgorithmParameterException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaticConfigLoanServiceTest extends PropertyInjectedUtil {

    @Autowired
    private StaticConfigLoanService loanService;

    @Before
    public void setUp() throws Exception {
        setFixedTime();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenApplyingLoanWithCorrectData_ShouldCreateLoan() throws Exception {
        LoanApplianceDTO dto = new LoanApplianceDTO(config.getLoanMinTermDays(), config.getLoanMinAmount());

        final LoanApplianceResultDTO result = loanService.applyForLoan(dto);
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertNotNull("it should generate id", result.getLoanId());

        final Loan loan = loanRepository.findByLoanIdOrderByRequestedDate(result.getLoanId()).iterator().next();

        assertEquals(config.getPrincipal(), loan.getPrincipal());
        assertEquals(LocalDateTime.now(), loan.getRequestedDate());
        assertEquals(LocalDateTime.now().plusDays(loan.getTerm()), loan.getDueDate());

    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanWithNullValues_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO());
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowMinTerm_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(config.getLoanMinTermDays() - 1, config.getLoanMinAmount()));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowMinAmount_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(config.getLoanMinTermDays(), config.getLoanMinAmount() - 1));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBelowAboveMaxTerm_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(config.getLoanMaxTermDays() + 1, config.getLoanMinAmount()));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanAboveMaxAmount_ShouldFailOnValidation() throws Exception {
        loanService.applyForLoan(new LoanApplianceDTO(config.getLoanMinTermDays(), config.getLoanMaxAmount() + 1));
    }

    @Test(expected = InvalidInputException.class)
    public void whenApplyingLoanBetweenRestrictedHours_ShouldFailOnValidation() throws Exception {
        setFixedTime(LocalDateTime.now().withHourOfDay(config.getRejectStartHour()).plusMinutes(1));

        loanService.applyForLoan(new LoanApplianceDTO(config.getLoanMinTermDays(), config.getLoanMaxAmount()));
    }

    @Test
    public void whenApplyingLoanBetweenRestrictedHoursWithMinAmount_ShouldCreateLoan() throws Exception {
        final LocalDateTime now = setFixedTime(LocalDateTime.now().withHourOfDay(config.getRejectStartHour()).plusMinutes(1));

        LoanApplianceDTO dto = new LoanApplianceDTO(config.getLoanMinTermDays(), config.getLoanMinAmount());

        final LoanApplianceResultDTO result = loanService.applyForLoan(dto);
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertNotNull("it should generate id", result.getLoanId());

        final Loan loan = loanRepository.findByLoanIdOrderByRequestedDate(result.getLoanId()).iterator().next();

        assertEquals(config.getPrincipal(), loan.getPrincipal());
        assertEquals(LocalDateTime.now(), loan.getRequestedDate());
        assertEquals(now.plusDays(config.getLoanMinTermDays()), loan.getDueDate());
    }

    //----------------------------------------- Loan extend  tests -----------------------------------------

    @Test
    public void whenExtendingLoan_ShouldUpdateExistingLoan() throws Exception {
        final Loan initialLoan = prepareSampleLoan();
        final LoanExtensionResultDTO result = loanService.extendLoan(new LoanExtensionDTO(initialLoan.getLoanId()));

        assertEquals(initialLoan.getLoanId(), result.getLoanId());
        final LocalDateTime expectedDueDate = initialLoan.getDueDate().plusDays(config.getExtendTermDays());
        assertEquals(expectedDueDate, result.getDueDate());

        final Loan updatedLoan = loanRepository.findById(result.getId()).get();
        assertEquals(initialLoan.getLoanId(), updatedLoan.getLoanId());
        assertEquals(expectedDueDate, updatedLoan.getDueDate());
        assertEquals(LocalDateTime.now(), updatedLoan.getRequestedDate());
        assertEquals(initialLoan.getAmount(), updatedLoan.getAmount());
        assertEquals(initialLoan.getPrincipal(), updatedLoan.getPrincipal());
        assertEquals(config.getExtendTermDays(), updatedLoan.getTerm());
        //check if old record was updated
        final Loan previousLoan = loanRepository.findById(initialLoan.getId()).get();
        assertEquals(LocalDateTime.now(), previousLoan.getLastUpdate());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenExtendingNonExistingLoan_ShouldThrowException() throws Exception {
        loanService.extendLoan(new LoanExtensionDTO(UUID.randomUUID()));
    }

}