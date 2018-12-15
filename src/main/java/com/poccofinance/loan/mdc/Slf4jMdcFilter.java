package com.poccofinance.loan.mdc;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * A servlet that adds a key to the Mapped Diagnostic Context (MDC) to each request so you can print a unique id in the logg messages of each request.
 * It also add the key as a header in the response so the caller of the request can provide you the id to browse the logs.
 *
 * @see MdcFilterConfiguration
 **/
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
class Slf4jMdcFilter extends OncePerRequestFilter {

    private final String mdcTokenKey;
    private final String correlationHeaderName;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
        throws java.io.IOException, ServletException {
        try {
            final String correlationId = getOrCreateCorrelationId(request, correlationHeaderName);
            MDC.put(mdcTokenKey, correlationId);
            response.addHeader(correlationHeaderName, correlationId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(mdcTokenKey);
        }
    }

    private String getOrCreateCorrelationId(HttpServletRequest request, String correlationHeaderName) {
        return Optional.ofNullable(request.getHeader(correlationHeaderName))
            .orElseGet(() -> UUID.randomUUID().toString());
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}