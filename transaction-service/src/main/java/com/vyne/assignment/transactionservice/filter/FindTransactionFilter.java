package com.vyne.assignment.transactionservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter will add a custom header to all find endpoints
 */
@Component
@Slf4j
public class FindTransactionFilter implements Filter {

    private static final String X_CUSTOM_HEADER = "X-Custom-Header";

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        // Adding a custom header
        res.setHeader(X_CUSTOM_HEADER, "Custom header value");

        chain.doFilter(request, response);
    }
}

