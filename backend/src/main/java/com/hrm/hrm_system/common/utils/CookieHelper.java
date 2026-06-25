package com.hrm.hrm_system.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieHelper {
    public Cookie generateLoginCookies(String token){
        //set cookies
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        return cookie;
//        response.addCookie(cookie);
    }
}
