package com.poccofinance.loan.dto;

import com.poccofinance.loan.validators.CorrectAmount;
import com.poccofinance.loan.validators.CorrectTermPeriod;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class LoanApplianceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @CorrectTermPeriod
    private Integer term;

    @NotNull
    @CorrectAmount
    private Long amount;

}
