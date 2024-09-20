package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.exception.NumerosDeHostsInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraMascara implements Calculadora<Integer, Integer> {

    @Override
    public Integer calcular(Integer request) throws NumerosDeHostsInvalidoException {
        verificarNumeroDeHosts(request);
        long numeroDeIpsNecessarios = request + 2L;
        if (numeroDeIpsNecessarios  % 2 == 1) {
            numeroDeIpsNecessarios++;
        }
        Integer mascara = 30;
        while (numeroDeIpsNecessarios > 4) {
            mascara--;
            numeroDeIpsNecessarios /= 2;
        }

        return mascara;
    }

    private void verificarNumeroDeHosts(Integer request) throws NumerosDeHostsInvalidoException {
        if (request > 2147483646) {
            throw new NumerosDeHostsInvalidoException("Número de hosts desejado maior que o limite do IPV4");
        }
        if (request == 0) {
            throw new NumerosDeHostsInvalidoException("Número de hosts desejado é 0!");
        }
        if (request < 0) {
            throw new NumerosDeHostsInvalidoException("Número de hosts desejado é negativo!");
        }
    }

}
