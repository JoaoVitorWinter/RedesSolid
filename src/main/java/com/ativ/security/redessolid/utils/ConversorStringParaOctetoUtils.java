package com.ativ.security.redessolid.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConversorStringParaOctetoUtils {

    public Integer[] converterStringParaOctetos(String ip) {
        return Arrays.stream(ip.split("\\."))
                .map(Integer::parseInt).toArray(Integer[]::new);
    }
}
