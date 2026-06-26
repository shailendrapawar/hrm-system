package com.hrm.hrm_system.common.utils;

import com.hrm.hrm_system.common.dtos.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContextBuilder {
    @Autowired
    UUIDHelper uuidHelper;

    public AppContext create(HttpServletRequest request){
        AppContext context=new AppContext();
        //1: set initial values
        context.setRequestId(uuidHelper.generate());
        context.setCreatedAt(LocalDateTime.now());
        context.setIpAddress(request.getRemoteAddr());
        return context;
    }

}
