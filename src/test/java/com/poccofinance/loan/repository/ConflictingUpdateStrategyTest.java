package com.poccofinance.loan.repository;

import com.poccofinance.loan.Loan;
import com.poccofinance.loan.PropertyInjectedUtil;
import com.poccofinance.loan.exception.ResourceConflictedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConflictingUpdateStrategyTest extends PropertyInjectedUtil {


    private ConflictingUpdateStrategy<Loan> updateStrategy;

    @Before
    public void setUp() throws Exception {
        updateStrategy = new ConflictingUpdateStrategy<>(loanRepository);
    }


    @Test(expected = ResourceConflictedException.class)
    public void whenConcurrentlyUpdateSameResource_ShouldThrowException() throws Exception {
        final Loan loan = updateStrategy.updateResource(prepareSampleLoan());
        updateStrategy.updateResource(loan);
        updateStrategy.updateResource(loan);
    }
}