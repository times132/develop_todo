package com.jejujg.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

    public Cookie createCookie(String cookieName, String value, long maxage){
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) maxage);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;

        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cookieName))
                return cookie;
        }

        return null;
    }
}
