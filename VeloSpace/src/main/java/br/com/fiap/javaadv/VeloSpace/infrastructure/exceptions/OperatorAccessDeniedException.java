package br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions;

public class OperatorAccessDeniedException extends ForbiddenException {

    public OperatorAccessDeniedException() {
        super("Seu cadastro de operador ainda não foi aprovado.");
    }

}
