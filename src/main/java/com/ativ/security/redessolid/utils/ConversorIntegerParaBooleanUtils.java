package com.ativ.security.redessolid.utils;

import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class ConversorIntegerParaBooleanUtils {
    public boolean[][] arrayDeNumerosParaQuatroOctetos(Integer[] octetos) throws QuantidadeDeOctetosInvalidoException {
        verificarNumeroDeOctetos(octetos);
        boolean[][] ipBits = new boolean[4][8];
        for (int i = 0; i < 4; i++) {
            int octeto = octetos[i];
            for (int j = 0; j < 8; j++) {
                if (octeto == 0) {
                    break;
                }
                int valorBit = (int) Math.pow(2, (7 - j));
                ipBits[i][j] = octeto >= valorBit;
                if (ipBits[i][j]) {
                    octeto -= valorBit;
                }
            }
        }
        return ipBits;
    }

    private void verificarNumeroDeOctetos(Integer[] octetos) throws QuantidadeDeOctetosInvalidoException {
        if (octetos.length != 4) {
            throw new QuantidadeDeOctetosInvalidoException();
        }
    }

    private void verificarSeMascaraValida(Integer mascara) throws MascaraInvalidaException {
        if (mascara < 1) {
            throw new MascaraInvalidaException("Máscara deve ser no mínimo 1 para uma rede válida!");
        }
        if (mascara > 30) {
            throw new MascaraInvalidaException("Máscara deve ser no máximo 30 para uma rede válida!");
        }
    }

    public boolean[][] mascaraParaQuatroOctetos(Integer mascara) throws MascaraInvalidaException, QuantidadeDeOctetosInvalidoException {
        verificarSeMascaraValida(mascara);
        Integer[] octetos = mascaraParaArrayDeNumerosOctetos(mascara);
        return arrayDeNumerosParaQuatroOctetos(octetos);
    }

    private Integer[] mascaraParaArrayDeNumerosOctetos(Integer mascara) {
        Integer[] octetos = new Integer[4];
        for (int i = 0; i < 4; i++) {
            if (mascara >= 8) {
                octetos[i] = 255;
                mascara -= 8;
            } else if (mascara > 0) {
                octetos[i] = (int) (255 - (Math.pow(2, 8 - mascara) - 1));
                mascara = 0;
            } else {
                octetos[i] = 0;
            }
        }
        return octetos;
    }
}
