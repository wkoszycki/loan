package com.poccofinance.loan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanExtensionDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull
    private UUID loanId;

}
