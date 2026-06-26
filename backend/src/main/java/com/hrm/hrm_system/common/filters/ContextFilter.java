package com.hrm.hrm_system.common.filters;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.utils.ContextBuilder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ContextFilter extends  OncePerRequestFilter {

    private final  ContextBuilder contextBuilder;

    public ContextFilter(ContextBuilder contextBuilder){
        this.contextBuilder=contextBuilder;
    }

    @Override
    protected  void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    )throws IOException, ServletException {
        AppContext context = contextBuilder.create(request);

        request.setAttribute("context", context);
        chain.doFilter(request, response);
    }
}
