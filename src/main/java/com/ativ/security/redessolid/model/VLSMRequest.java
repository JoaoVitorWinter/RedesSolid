package com.ativ.security.redessolid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VLSMRequest {
    private Map<String, Integer> VLANs;
    private IPComMascara ipComMascara;
}
