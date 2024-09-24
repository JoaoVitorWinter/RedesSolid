package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import com.ativ.security.redessolid.model.IPComMascara;
import com.ativ.security.redessolid.model.Rede;
import com.ativ.security.redessolid.utils.ConversorIntegerParaBooleanUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class CalculadoraRede implements Calculadora<IPComMascara, Rede> {
    private ConversorIntegerParaBooleanUtils conversorIntegerParaBooleanUtils;

    @Override
    public Rede calcular(IPComMascara request) throws QuantidadeDeOctetosInvalidoException, MascaraInvalidaException {
        Rede rede = new Rede();
        Integer mascara = request.getMascara();
        Integer[] quatroOctetosDoIp = Arrays.stream(request.getIp().split("\\."))
                .map(Integer::parseInt).toArray(Integer[]::new);
        boolean[][] ipBits = conversorIntegerParaBooleanUtils.
                arrayDeNumerosParaQuatroOctetos(quatroOctetosDoIp);
        boolean[][] mascaraBits = conversorIntegerParaBooleanUtils.mascaraParaQuatroOctetos(mascara);
        rede.setIpRede(calcularIpRede(ipBits, mascaraBits));
        rede.setIpBroadcast(calcularIpBroadcast(ipBits, mascaraBits));
        rede.setNumeroDeHosts((int) (Math.pow(2, 32 - mascara) - 2));
        rede.setMascara(mascara);
        return rede;

    }

    private String calcularIpBroadcast(boolean[][] ipBits, boolean[][] mascaraBits) {
        Integer[] octetos = {255, 255, 255, 255};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (!ipBits[i][j] && mascaraBits[i][j]) {
                    octetos[i] -= (int) Math.pow(2,  7 - j);
                }
            }
        }
        return transformarOctetosEmIp(octetos);

    }



    private String calcularIpRede(boolean[][] ipBits, boolean[][] mascaraBits) {
        Integer[] octetos = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (ipBits[i][j] && mascaraBits[i][j]) {
                    octetos[i] += (int) Math.pow(2,  7 - j);
                }
            }
        }
        return transformarOctetosEmIp(octetos);
    }

    private String transformarOctetosEmIp(Integer[] octetos) {
        return String.join(".", Arrays.stream(octetos)
                .map(Object::toString).toArray(String[]::new));
    }
}
