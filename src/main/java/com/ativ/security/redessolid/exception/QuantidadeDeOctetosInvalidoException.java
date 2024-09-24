package com.ativ.security.redessolid.exception;

public class QuantidadeDeOctetosInvalidoException extends Exception {

    public QuantidadeDeOctetosInvalidoException() {
        super("Quantidade de octetos inválidos! Única quantidade aceita é 4!");
    }
}
