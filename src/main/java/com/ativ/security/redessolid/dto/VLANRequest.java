package com.ativ.security.redessolid.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VLANRequest {
    @NotEmpty
    private String nome;
    @NotNull @Positive
    private Integer numeroDeHosts;
}
