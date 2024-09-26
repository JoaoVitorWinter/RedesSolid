package com.ativ.security.redessolid.security.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
    public Cookie criarCookie(String jwt) {
        Cookie cookie = new Cookie("JWT_USER", jwt);
        cookie.setMaxAge(900);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        return cookie;
    }

    public Cookie removerCookieJwt() {
        Cookie cookie = new Cookie("JWT_USER", "");
        cookie.setMaxAge(1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        return cookie;
    }
}
