package com.poccofinance.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.poccofinance.loan.converters.HandMadeLoanConverter;
import com.poccofinance.loan.converters.LoanConverter;
import com.poccofinance.loan.repository.ConflictingUpdateStrategy;
import com.poccofinance.loan.repository.LoanRepository;
import com.poccofinance.loan.repository.ResourceUpdateStrategy;
import com.poccofinance.loan.service.StaticConfigLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.poccofinance.loan"})
@EnableConfigurationProperties
public class LoanApplication {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanConverter loanConverter;

    @Autowired
    private StaticConfigLoanService staticConfigLoanService;

    public static void main(String[] args) {
        SpringApplication.run(LoanApplication.class, args);
        log.info("Loan app successfully started");
    }

    @Bean
    LoanConverter createLoanConverter() {
        return new HandMadeLoanConverter();
    }

    @Bean
    ObjectMapper createObjectMapperWithJodaSupport() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    ResourceUpdateStrategy<Loan> createLoanService() {
        return new ConflictingUpdateStrategy<>(loanRepository);
    }

}