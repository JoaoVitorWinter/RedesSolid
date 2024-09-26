package com.ativ.security.redessolid.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConversorOctetoParaStringUtils {
    public String transformarOctetosEmIp(Integer[] octetos) {
        return String.join(".", Arrays.stream(octetos)
                .map(Object::toString).toArray(String[]::new));
    }
}
