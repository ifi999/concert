package com.hhp.concert.support.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpRequest);
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(httpResponse);

        final String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);
        httpResponse.setHeader("X-Transaction-ID", transactionId);

        try {
            logger.info("Transaction ID: {}, Request URI: {}", transactionId, cachedBodyHttpServletRequest.getRequestURI());
            logger.info("Transaction ID: {}, Request Body: {}", transactionId, cachedBodyHttpServletRequest.getRequestBody());

            filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

            logger.info("Transaction ID: {}, Response Status: {}", transactionId, cachedBodyHttpServletResponse.getStatus());
            if (httpResponse.getContentType() != null && httpResponse.getContentType().contains("application/json")) {
                String responseBody = cachedBodyHttpServletResponse.getResponseBody();
                logger.info("Transaction ID: {}, Response Body: {}", transactionId, responseBody);
            }
        } catch (Exception e) {
            logger.error("Transaction ID: {}, Exception: {}", transactionId, e.getMessage(), e);
        } finally {
            MDC.clear();
        }

    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
