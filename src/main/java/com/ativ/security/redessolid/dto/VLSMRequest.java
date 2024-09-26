package com.ativ.security.redessolid.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VLSMRequest {
    @NotEmpty @Valid
    private List<VLANRequest> vlans;
    private IPComMascara ipComMascara;
}
