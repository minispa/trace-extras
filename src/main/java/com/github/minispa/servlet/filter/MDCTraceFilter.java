package com.github.minispa.servlet.filter;

import static com.github.minispa.MDCTraceHelper.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class MDCTraceFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String traceMark = setNewIfAbsent(request.getHeader(TraceMark));
        try {
            log.info("MDCTraceFilter - doFilter created: {}", traceMark);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            log.info("MDCTraceFilter - doFilter cleared: {}", traceMark);
            clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
