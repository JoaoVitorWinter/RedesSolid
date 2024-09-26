package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import com.ativ.security.redessolid.dto.IPComMascara;
import com.ativ.security.redessolid.dto.Rede;
import com.ativ.security.redessolid.utils.ConversorIntegerParaBooleanUtils;
import com.ativ.security.redessolid.utils.ConversorOctetoParaStringUtils;
import com.ativ.security.redessolid.utils.ConversorStringParaOctetoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculadoraRede implements Calculadora<IPComMascara, Rede> {
    private ConversorIntegerParaBooleanUtils conversorIntegerParaBooleanUtils;
    private ConversorStringParaOctetoUtils conversorStringParaOctetoUtils;
    private ConversorOctetoParaStringUtils conversorOctetoParaStringUtils;

    @Override
    public Rede calcular(IPComMascara request) throws QuantidadeDeOctetosInvalidoException, MascaraInvalidaException {
        Rede rede = new Rede();
        Integer mascara = request.getMascara();
        Integer[] quatroOctetosDoIp = conversorStringParaOctetoUtils.converterStringParaOctetos(request.getIp());
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
        return conversorOctetoParaStringUtils.transformarOctetosEmIp(octetos);

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
        return conversorOctetoParaStringUtils.transformarOctetosEmIp(octetos);
    }
}
