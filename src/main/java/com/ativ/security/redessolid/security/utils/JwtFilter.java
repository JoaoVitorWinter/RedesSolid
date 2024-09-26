package com.ativ.security.redessolid.security.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
            try {
                Cookie[] cookies = request.getCookies();
                Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals("JWT_USER")).findFirst();
                if (cookieOptional.isPresent()) {
                    Cookie cookie = cookieOptional.get();
                    String jwt = cookie.getValue();
                    Authentication authentication = jwtUtils.validarToken(jwt);
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                    if (!uri.equals("/autenticacao/logout")) {
                        String tokenRevalidado = jwtUtils.criarToken((UserDetails) authentication.getPrincipal());
                        cookie = cookieUtils.criarCookie(tokenRevalidado);
                        response.addCookie(cookie);
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception exception) {
                if (!isPublicEndPoint(uri, method)) {
                    response.setStatus(401);
                    return;
                }
            }

        filterChain.doFilter(request, response);

    }

    private boolean isPublicEndPoint(String uri, String method) {
        return (uri.equals("/autenticacao/login") && method.equals("POST"))
                || (uri.contains("/livros") && method.equals("GET"))
                || (uri.equals("/clientes") && method.equals("POST"))
                || (uri.contains("/editoras") && method.equals("GET"))
                || (uri.contains("/autores") && method.equals("GET"))
                || (uri.contains("/categorias") && method.equals("GET"));
    }
}
