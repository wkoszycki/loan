package com.poccofinance.loan.dto;

import com.poccofinance.loan.validators.CorrectAmount;
import com.poccofinance.loan.validators.CorrectTermPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanApplianceDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull
    @CorrectTermPeriod
    private Integer term;

    @NotNull
    @CorrectAmount
    private Long amount;

}
