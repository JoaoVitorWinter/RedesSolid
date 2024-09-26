package com.ativ.security.redessolid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Perfil implements GrantedAuthority {
    PROFESSOR("Professor"),
    ALUNO("Aluno");

    private String nome;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
