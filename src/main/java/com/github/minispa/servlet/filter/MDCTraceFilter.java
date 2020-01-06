package com.github.minispa.servlet.filter;

import com.github.minispa.MDCTraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class MDCTraceFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceMark = request.getHeader(MDCTraceHelper.TraceMark);
        if(traceMark == null || traceMark.trim().length() == 0) {
            traceMark = MDCTraceHelper.newTraceMark();;
        }
        MDC.put(MDCTraceHelper.TraceMark, traceMark);
        try {
            log.info("MDCTraceFilter - doFilter created: {}", traceMark);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
            log.info("MDCTraceFilter - doFilter cleared: {}", traceMark);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
