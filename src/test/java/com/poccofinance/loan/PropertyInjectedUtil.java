package com.poccofinance.loan;

import com.poccofinance.loan.repository.LoanRepository;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class PropertyInjectedUtil {

    @Autowired
    protected LoanRepository loanRepository;

    @Autowired
    protected TypeSafeConfig config;


    static public LocalDateTime setFixedTime(LocalDateTime timeToSet) {
        return setFixedTime(timeToSet.toDateTime().getMillis());
    }

    /**
     * Sets fixed Time for all tests. It's necessary for tests to be repeatable,
     * so whenever tests will run between restricted hours will always pass.
     *
     * @return now
     */
    protected LocalDateTime setFixedTime() {
        return setFixedTime(LocalDateTime.now().withHourOfDay(config.getRejectStopHour()).plusMinutes(1));
    }

    static private LocalDateTime setFixedTime(long timeToSet) {
        DateTimeUtils.setCurrentMillisFixed(timeToSet);
        return LocalDateTime.now();
    }

    protected Loan prepareSampleLoan() {
        final Loan loan = Loan.builder()
            .loanId(UUID.randomUUID())
            .requestedDate(LocalDateTime.now())
            .term(config.getLoanMinTermDays())
            .principal(config.getPrincipal())
            .amount(config.getLoanMinAmount())
            .dueDate(LocalDateTime.now().plusDays(config.getLoanMinTermDays()))
            .build();
        return loanRepository.save(loan);
    }
}
