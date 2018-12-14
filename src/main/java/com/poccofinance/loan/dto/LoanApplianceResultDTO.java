package com.poccofinance.loan.dto;

import lombok.*;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@EqualsAndHashCode
public class LoanApplianceResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID loanId;
    private Integer term;
    private Long amount;
    private LocalDateTime startDate;

}