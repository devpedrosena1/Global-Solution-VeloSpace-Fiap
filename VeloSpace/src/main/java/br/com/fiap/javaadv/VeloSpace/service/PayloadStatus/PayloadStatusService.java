package br.com.fiap.javaadv.VeloSpace.service.PayloadStatus;

public interface PayloadStatusService<T, ID> {

    T findByCode(String code);

    T getRequiredByCode(String code);

}
