package io.github.keeeper.learningspring.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest){
            var httpRequest = (HttpServletRequest) servletRequest;
            logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURL());
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

}
