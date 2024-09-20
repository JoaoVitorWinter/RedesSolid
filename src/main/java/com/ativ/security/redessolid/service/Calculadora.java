package com.ativ.security.redessolid.service;

import org.springframework.stereotype.Service;

@Service
public interface Calculadora<I, O> {
    O calcular(I request) throws Exception;
}
