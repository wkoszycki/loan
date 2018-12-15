package com.poccofinance.loan.mdc;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "mdc-filter")
class MdcFilterConfiguration {


    private String mdcTokenKey;
    private String correlationHeaderName;

    @Bean
    public FilterRegistrationBean registerMdcFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new Slf4jMdcFilter(mdcTokenKey, correlationHeaderName));
        registrationBean.setOrder(2);
        return registrationBean;
    }
}