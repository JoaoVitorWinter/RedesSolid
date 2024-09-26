package com.ativ.security.redessolid.security.utils;

import com.ativ.security.redessolid.security.service.AutenticacaoService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final AutenticacaoService autenticacaoService;
    @Value("${jwt.secret}")
    private String secret;

        public String criarToken(UserDetails userDetails) {
        Instant instanteAssinado = Instant.now();
        Instant instanteExpirado = instanteAssinado.plus(15, ChronoUnit.MINUTES);
        String[] authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new);
        return JWT.create().withIssuer("Professor Roberto")
                .withIssuedAt(instanteAssinado)
                .withExpiresAt(instanteExpirado)
                .withSubject(userDetails.getUsername())
                .withArrayClaim("authorities", authorities)
                .sign(Algorithm.HMAC256(secret));
    }

    public Authentication validarToken(String jwt) {
        String username = JWT.require(Algorithm.HMAC256(secret))
                .build().verify(jwt).getSubject();
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
