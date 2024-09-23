package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.model.IPComMascara;
import com.ativ.security.redessolid.model.Rede;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CalculadoraRede implements Calculadora<IPComMascara, Rede> {


    @Override
    public Rede calcular(IPComMascara request) throws Exception {
        verificarRequestRede(request);
        Integer[] quatroOctetosDoIp = Arrays.stream(request.getIp().split("\\."))
                .map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(Arrays.toString(quatroOctetosDoIp));
        boolean[] ipBits = new boolean[32];
        System.out.println(Arrays.toString(ipBits));
        for (int i = 0; i < ipBits.length; i++) {
            int octeto = quatroOctetosDoIp[i / 8];
            if (octeto == 0) {
                i = i + (8 - (i % 8) - 1);
                continue;
            }
            System.out.println(octeto);
            int valorBit = (int) Math.pow(2, (8 - (i % 8) - 1));
            ipBits[i] = octeto >= valorBit;
            if (ipBits[i]) {
                quatroOctetosDoIp[i / 8] = octeto - valorBit;
            }


        }
        System.out.println(Arrays.toString(ipBits));
        return null;
    }

    private void verificarRequestRede(IPComMascara request) {

    }
}
