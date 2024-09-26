package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.dto.UsuarioPost;
import com.ativ.security.redessolid.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    void criarUsuario(UsuarioPost usuarioPost);
}
