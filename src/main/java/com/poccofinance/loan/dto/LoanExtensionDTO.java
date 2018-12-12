package com.poccofinance.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanExtensionDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private UUID loanId;

}
