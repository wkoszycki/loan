package com.poccofinance.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poccofinance.loan.dto.LoanApplianceDTO;
import com.poccofinance.loan.dto.LoanApplianceResultDTO;
import com.poccofinance.loan.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanRestControllerIT extends PropertyInjectedUtil {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1544604544700L);
    }

    @Test
    public void whenAddingLoan_ShouldReturnCreated() throws Exception {
        performRequest(new LoanApplianceDTO(minTerm, minAmount), status().isCreated());
    }


    @Test
    public void whenAddingLoanBetweenRestrictedHoursWithMinAmount_ShouldReturnCreated() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(LocalDateTime.now()
            .withHourOfDay(minHour)
            .plusMinutes(1)
            .toDateTime()
            .getMillis());

        performRequest(new LoanApplianceDTO(minTerm, minAmount), status().isCreated());
    }

    @Test
    public void whenAddingLoanWithInvalidTerm_ShouldReturnBadRequest() throws Exception {
        performRequest(new LoanApplianceDTO(minTerm - 1, minAmount), status().isBadRequest());
    }

    @Test
    public void whenAddingLoanWithInvalidAmount_ShouldReturnBadRequest() throws Exception {
        performRequest(new LoanApplianceDTO(minTerm, minAmount - 1), status().isBadRequest());
    }


    @Test
    public void whenAddingLoanBetweenRestrictedHoursWithMaxAmount_ShouldReturnBadRequest() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(LocalDateTime.now()
            .withHourOfDay(minHour)
            .plusMinutes(1)
            .toDateTime()
            .getMillis());

        performRequest(new LoanApplianceDTO(minTerm, maxAmount), status().isBadRequest());
    }


    private ResultActions performRequest(Object request, ResultMatcher matcher) throws Exception {
        return mockMvc.perform(post("/loans")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(matcher);
    }

}