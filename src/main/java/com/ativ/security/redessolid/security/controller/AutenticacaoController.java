package com.ativ.security.redessolid.security.controller;

import com.ativ.security.redessolid.security.utils.CookieUtils;
import com.ativ.security.redessolid.security.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
@AllArgsConstructor
public class AutenticacaoController {
    private AuthenticationProvider authenticationProvider;
    private CookieUtils cookieUtils;
    private JwtUtils jwtUtils;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.usuario(), loginDTO.senha());
        authentication = authenticationProvider.authenticate(authentication);
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.criarToken(userDetails);
            Cookie cookie = cookieUtils.criarCookie(jwt);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok((UserDetails) authentication.getPrincipal());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = cookieUtils.removerCookieJwt();
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }

}
