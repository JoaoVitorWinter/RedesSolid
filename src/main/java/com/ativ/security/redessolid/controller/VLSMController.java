package com.ativ.security.redessolid.controller;

import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.NumerosDeHostsInvalidoException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import com.ativ.security.redessolid.exception.RedeComNumeroLimitadosDeHostsException;
import com.ativ.security.redessolid.dto.VLSMRequest;
import com.ativ.security.redessolid.service.CalculadoraVLSM;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vlsm")
@AllArgsConstructor
public class VLSMController {
    private CalculadoraVLSM calculadoraVLSM;

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PostMapping
    public ResponseEntity<?> getVlans(@Valid @RequestBody VLSMRequest vlsmRequest) {
        try {
            return ResponseEntity.status(200).body(calculadoraVLSM.calcular(vlsmRequest));
        } catch (RedeComNumeroLimitadosDeHostsException | QuantidadeDeOctetosInvalidoException |
                 MascaraInvalidaException | NumerosDeHostsInvalidoException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
