package com.ativ.security.redessolid.dto;

import com.ativ.security.redessolid.entity.Perfil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UsuarioPost {
    @NotEmpty
    private String usuario;
    @NotEmpty
    private String senha;
    @NotNull
    private Perfil perfil;
}
