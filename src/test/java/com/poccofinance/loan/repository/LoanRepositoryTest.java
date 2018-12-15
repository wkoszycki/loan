package com.poccofinance.loan.repository;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.PropertyInjectedUtil;
import com.poccofinance.loan.converters.LoanConverter;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanRepositoryTest extends PropertyInjectedUtil {

    @Autowired
    LoanConverter converter;

    @Test
    public void whenNonExistingLoan_ShouldReturnEmpty() {
        final Optional<Loan> maybeLoan = loanRepository.findFirstByLoanIdOrderByRequestedDateDesc(UUID.randomUUID());
        assertFalse(maybeLoan.isPresent());

    }
    @Test
    public void whenMultipleLoansPersisted_ShouldReturnLatest() {
        final Loan initialLoan = prepareSampleLoan();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        final Loan loanToBePersisted = converter.shallowCopy(initialLoan)
            .id(null)
            .version(null)
            .requestedDate(LocalDateTime.now())
            .dueDate(tomorrow)
            .build();
        loanRepository.save(loanToBePersisted);

        final Loan expectedLoan = loanRepository.save(converter.shallowCopy(initialLoan)
            .requestedDate(LocalDateTime.now())
            .build());

        final Loan actualLoan = loanRepository.findFirstByLoanIdOrderByRequestedDateDesc(initialLoan.getLoanId()).get();
        assertEquals(expectedLoan.getId(), actualLoan.getId());
    }
}