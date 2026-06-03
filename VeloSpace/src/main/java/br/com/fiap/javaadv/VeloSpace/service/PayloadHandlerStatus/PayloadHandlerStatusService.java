package br.com.fiap.javaadv.VeloSpace.service.PayloadHandlerStatus;

public interface PayloadHandlerStatusService<T, ID> {

    T findByCode(String code);

    T getRequiredByCode(String code);

}
