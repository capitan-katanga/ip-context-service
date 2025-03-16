package com.mercadolibre.ipcontext.config;

import com.mercadolibre.ipcontext.util.Constants;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;

        var spanId = String.valueOf(System.currentTimeMillis());
        var correlationId = Optional.ofNullable(httpServletRequest.getHeader(Constants.CORRELATION_ID))
                .orElse(UUID.randomUUID().toString().replace("-", ""));

        MDC.put(Constants.CORRELATION_ID, correlationId);
        MDC.put(Constants.SPAN_ID, spanId);

        log.info("-------------- START NEW TRANSACTION --------------");

        var httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.addHeader(Constants.CORRELATION_ID, correlationId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        MDC.clear();
    }

}
