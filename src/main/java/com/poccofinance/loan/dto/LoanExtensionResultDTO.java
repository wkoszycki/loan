package com.poccofinance.loan.dto;

import lombok.*;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.UUID;

@Data
public class LoanExtensionResultDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private UUID loanId;
    private LocalDateTime dueDate;

}
