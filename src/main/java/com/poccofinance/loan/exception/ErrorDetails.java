package com.poccofinance.loan.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor
class ErrorDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private String message;
    private String details;

    ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
