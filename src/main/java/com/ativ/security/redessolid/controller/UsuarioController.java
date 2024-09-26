package com.ativ.security.redessolid.controller;

import com.ativ.security.redessolid.dto.UsuarioPost;
import com.ativ.security.redessolid.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioPost usuarioPost) {
        try {
            usuarioService.criarUsuario(usuarioPost);
            return ResponseEntity.status(201).build();
        } catch(DataIntegrityViolationException exception) {
            return ResponseEntity.status(400).build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
