package br.com.fiap.javaadv.VeloSpace.service.PayloadPriority;

import java.util.List;

public interface PayloadPriorityService<T, ID> {

    List<T> findAll();

}