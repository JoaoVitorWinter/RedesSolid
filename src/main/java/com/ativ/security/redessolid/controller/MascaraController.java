package com.ativ.security.redessolid.controller;


import com.ativ.security.redessolid.exception.NumerosDeHostsInvalidoException;
import com.ativ.security.redessolid.service.CalculadoraMascara;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mascara")
@AllArgsConstructor
public class MascaraController {
    private CalculadoraMascara calculadoraMascara;

    @GetMapping("/quantidadeDeHosts")
    public ResponseEntity<?> calcularMascaraPorQuantidadeDeHosts(@RequestParam (name = "quantidade") Integer quantidadeDeHosts) {
        try {
            return ResponseEntity.status(200).body(calculadoraMascara.calcular(quantidadeDeHosts));
        } catch (NumerosDeHostsInvalidoException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
    }
}
