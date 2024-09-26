package com.ativ.security.redessolid.controller;


import com.ativ.security.redessolid.exception.NumerosDeHostsInvalidoException;
import com.ativ.security.redessolid.service.CalculadoraMascara;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mascara")
@AllArgsConstructor
public class MascaraController {
    private CalculadoraMascara calculadoraMascara;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/quantidadeDeHosts")
    public ResponseEntity<?> getMascaraPorNumeroDeHost(@RequestParam (name = "quantidade") Integer numeroHost) {
        try {
            return ResponseEntity.status(200).body(calculadoraMascara.calcular(numeroHost));
        } catch (NumerosDeHostsInvalidoException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
    }
}
