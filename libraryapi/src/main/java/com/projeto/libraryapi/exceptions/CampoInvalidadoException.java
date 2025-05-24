package com.projeto.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidadoException extends RuntimeException {

    @Getter
    private String campo;

    public CampoInvalidadoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

}
