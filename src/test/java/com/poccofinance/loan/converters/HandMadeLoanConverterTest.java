package com.poccofinance.loan.converters;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.PropertyInjectedUtil;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.dto.LoanExtensionResultDTO;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class HandMadeLoanConverterTest {

    private HandMadeLoanConverter converter;
    private Loan sampleLoan;

    @Before
    public void setUp() throws Exception {
        PropertyInjectedUtil.setFixedTime(LocalDateTime.now());
        converter = new HandMadeLoanConverter();

        sampleLoan = Loan.builder()
            .loanId(UUID.fromString("6a3dfd95-a457-4559-978f-69fc7332805c"))
            .amount(2L)
            .term(10)
            .principal(10.0)
            .requestedDate(LocalDateTime.now())
            .dueDate(LocalDateTime.now())
            .build();
    }

    @Test
    public void whenConvertLoanToApplianceResultDTO_ShouldReturnCorrectData() throws Exception {
        final LoanApplianceResultDTO expected = LoanApplianceResultDTO.builder()
            .loanId(sampleLoan.getLoanId())
            .term(sampleLoan.getTerm())
            .amount(sampleLoan.getAmount())
            .startDate(sampleLoan.getRequestedDate())
            .build();

        final LoanApplianceResultDTO actual = converter.convertLoan(sampleLoan);
        assertEquals(expected, actual);
    }

    @Test
    public void whenConvertExtendedLoan_ShouldReturnCorrectData() throws Exception {
        final LoanExtensionResultDTO expected = LoanExtensionResultDTO.builder()
            .loanId(sampleLoan.getLoanId())
            .dueDate(sampleLoan.getRequestedDate())
            .build();

        final LoanExtensionResultDTO actual = converter.convertExtendedLoan(sampleLoan);
        assertEquals(expected, actual);
    }

    @Test
    public void whenConvertExtendedLoanNull_ShouldNotThrowNPE() throws Exception {
        final LoanExtensionResultDTO actual = converter.convertExtendedLoan(null);
        Assert.assertNotNull(actual);
    }

    @Test
    public void whenConvertLoanNull_ShouldNotThrowNPE() throws Exception {
        final LoanApplianceResultDTO actual = converter.convertLoan(null);
        Assert.assertNotNull(actual);
    }

    @Test
    public void whenConvertDTONull_ShouldNotThrowNPE() throws Exception {
        final Loan.LoanBuilder actual = converter.convert(null);
        Assert.assertNotNull(actual);
    }

    @Test
    public void whenConvertLoanApplianceDTO_ShouldReturnCorrectData() throws Exception {
        final LoanApplianceDTO sampleData = new LoanApplianceDTO(1, 2L);
        final Loan actual = converter.convert(sampleData).build();
        Loan expected = Loan.builder()
            .amount(2L)
            .term(1)
            .requestedDate(LocalDateTime.now()).build();
        assertEquals(expected, actual);
    }

    @Test
    public void whenCreateShallowCopyOfLoan_ShouldReturnCorrectData() throws Exception {
        final Loan actual = converter.shallowCopy(sampleLoan).build();
        assertEquals(sampleLoan, actual);
        // call each getter so test don't depend on equals implementation
        assertEquals(sampleLoan.getLoanId(), actual.getLoanId());
        assertEquals(sampleLoan.getAmount(), actual.getAmount());
        assertEquals(sampleLoan.getTerm(), actual.getTerm());
        assertEquals(sampleLoan.getPrincipal(), actual.getPrincipal());
        assertEquals(sampleLoan.getRequestedDate(), actual.getRequestedDate());
        assertEquals(sampleLoan.getDueDate(), actual.getDueDate());
    }

}