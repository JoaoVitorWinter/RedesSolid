package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.dto.UsuarioPost;
import com.ativ.security.redessolid.entity.Usuario;
import com.ativ.security.redessolid.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;
    @Override
    public void criarUsuario(UsuarioPost usuarioPost) {
        Usuario usuario = new Usuario(usuarioPost);
        usuarioRepository.save(usuario);
    }
}
