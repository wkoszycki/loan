package com.poccofinance.loan;

import lombok.*;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Table(indexes = {@Index(columnList = "loanId", name = "LOAN_ID_IDX")})
@Entity
@Data
public class Loan implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;
    @Column(nullable = false)
    private UUID loanId;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Integer term;
    @Column(nullable = false)
    private Double principal;
    @Column(nullable = false)
    private LocalDateTime requestedDate;
    @Column(nullable = false)
    private LocalDateTime dueDate;

}
