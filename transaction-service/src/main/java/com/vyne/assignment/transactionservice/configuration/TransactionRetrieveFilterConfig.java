package com.vyne.assignment.transactionservice.configuration;

import com.vyne.assignment.transactionservice.filter.FindTransactionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure FindTransactionFilter to filter only the find endpoints
 */
@Configuration
public class TransactionRetrieveFilterConfig {

    @Bean
    public FilterRegistrationBean<FindTransactionFilter> registrationBean() {
        FilterRegistrationBean<FindTransactionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FindTransactionFilter());
        registrationBean.addUrlPatterns("/transactions/find/*");
        return registrationBean;
    }
}
