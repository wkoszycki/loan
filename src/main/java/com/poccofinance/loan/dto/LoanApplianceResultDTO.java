package com.poccofinance.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoanApplianceResultDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private UUID loanId;
    private Integer term;
    private Long amount;
    private LocalDateTime startDate;

}