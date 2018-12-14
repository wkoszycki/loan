package com.poccofinance.loan;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * Injects values from yml file.
 */
@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "loan")
public class TypeSafeConfig {

    @NotNull
    private Double principal;
    @NotNull
    private Integer extendTermDays;
    @NotNull
    private Long loanMinAmount;
    @NotNull
    private Long loanMaxAmount;
    @NotNull
    private Integer rejectStartHour;
    @NotNull
    private Integer rejectStopHour;
    @NotNull
    private Integer loanMinTermDays;
    @NotNull
    private Integer loanMaxTermDays;

}