package com.ativ.security.redessolid.controller;


import com.ativ.security.redessolid.model.IPComMascara;
import com.ativ.security.redessolid.service.CalculadoraRede;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rede")
@AllArgsConstructor
public class RedeController {
    private CalculadoraRede calculadoraRede;

    @PostMapping("/detalhes")
    public ResponseEntity<?> buscarDetalhesDaRede(@RequestBody IPComMascara ipComMascara) {
        try {
            return ResponseEntity.status(200).body(calculadoraRede.calcular(ipComMascara));
        } catch (Exception exception) {
            return  ResponseEntity.status(500).build();
        }
    }
}
