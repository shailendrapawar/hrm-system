package com.hrm.hrm_system.common.interceptors;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.utils.JWTHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    JWTHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,Object handler){

        String token=getToken(req);
        AppContext context =(AppContext)req.getAttribute("context");

        if(token==null){
            throw new AppException("Unauthorised", HttpStatus.UNAUTHORIZED);
        }

        Boolean isValid=jwtHelper.verifyToken(token);
        if(!isValid){
            throw  new AppException("Invalid token",HttpStatus.UNAUTHORIZED);
        }
        // extract user
        JWTUser user=jwtHelper.extractUser(token);
        context.setUser(user);

        //get & set permissions
        List<String> permissions=this.getPermissions(user.getRoles());
        context.setPermissions(permissions);

       return true;
    }

    private String getToken(HttpServletRequest req){
        Cookie[] cookies=req.getCookies();

        if(cookies==null) return null;

        for(Cookie c: cookies){
            if(c.getName().equals("token")){
                return c.getValue();
            }
        }

        return null;
    }

    private List<String> getPermissions(List<String> roles){
        List<String> permissions=new ArrayList<>();

        return permissions;
    }
}
