package com.ativ.security.redessolid.controller;


import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import com.ativ.security.redessolid.dto.IPComMascara;
import com.ativ.security.redessolid.service.CalculadoraRede;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rede")
@AllArgsConstructor
public class RedeController {
    private CalculadoraRede calculadoraRede;

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PostMapping("/detalhes")
    public ResponseEntity<?> getRedeCompleta(@RequestBody IPComMascara ipComMascara) {
        try {
            return ResponseEntity.status(200).body(calculadoraRede.calcular(ipComMascara));
        } catch (NumberFormatException exception) {
            return ResponseEntity.status(400).body("O IP deve conter apenas números e pontos!");
        } catch (QuantidadeDeOctetosInvalidoException | MascaraInvalidaException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return  ResponseEntity.status(500).build();
        }
    }
}
