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

@RunWith(JUnit4.class)
public class HandMadeLoanConverterTest {

    private HandMadeLoanConverter converter;
    private Loan sampleLoan;

    @Before
    public void setUp() throws Exception {
        PropertyInjectedUtil.setFixedTime(LocalDateTime.now());
        converter = new HandMadeLoanConverter();

        sampleLoan = new Loan();
        sampleLoan.setLoanId(UUID.fromString("6a3dfd95-a457-4559-978f-69fc7332805c"));
        sampleLoan.setAmount(2L);
        sampleLoan.setTerm(10);
        sampleLoan.setPrincipal(10.0);
        sampleLoan.setRequestedDate(LocalDateTime.now());
        sampleLoan.setDueDate(LocalDateTime.now());
    }

    @Test
    public void shouldConvertLoanToApplianceResultDTO() throws Exception {
        final LoanApplianceResultDTO expected = LoanApplianceResultDTO.builder()
            .loanId(sampleLoan.getLoanId())
            .term(sampleLoan.getTerm())
            .amount(sampleLoan.getAmount())
            .startDate(sampleLoan.getRequestedDate())
            .build();

        final LoanApplianceResultDTO actual = converter.convert(sampleLoan);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertExtendedLoan() throws Exception {
        final LoanExtensionResultDTO expected = LoanExtensionResultDTO.builder()
            .loanId(sampleLoan.getLoanId())
            .dueDate(sampleLoan.getRequestedDate())
            .build();

        final LoanExtensionResultDTO actual = converter.convertExtendedLoan(sampleLoan);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldConvertLoanApplianceDTO() throws Exception {
        final LoanApplianceDTO sampleData = new LoanApplianceDTO(1, 2L);
        final Loan actual = converter.convert(sampleData);
        Loan expected = new Loan();
        expected.setAmount(2L);
        expected.setTerm(1);
        expected.setRequestedDate(LocalDateTime.now());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldCreateShallowCopyOfLoan() throws Exception {
        Assert.assertEquals(sampleLoan, converter.shallowCopy(sampleLoan));
    }

}