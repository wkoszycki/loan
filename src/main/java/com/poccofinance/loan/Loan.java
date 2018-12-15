package com.poccofinance.loan;

import com.poccofinance.loan.repository.UpdatableResource;
import lombok.*;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * This entity is twice versioned. First - single row versioning @Version for concurrent update purposes.
 * Second via requestedDate and loanId as business id, so we will always keep update history.
 * Amount is kept as Long type, this would mean that for 1$ amount would be calculated in cents e.g 100.
 * For other currencies analogous basic currency values will be kept.
 */
@Builder
@Table(indexes = {@Index(columnList = "loanId,requestedDate", name = "LOAN_ID_REQUESTED_IDX", unique = true)})
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id","loanId"})
public class Loan implements Serializable, UpdatableResource {

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
    private LocalDateTime lastUpdate;

    @Override
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
