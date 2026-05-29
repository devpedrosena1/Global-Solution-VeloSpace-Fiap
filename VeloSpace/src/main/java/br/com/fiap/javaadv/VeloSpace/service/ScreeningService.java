package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.Screening;

public interface ScreeningService {

    Screening findById(Long id);
    Screening create(Screening screening);
    Screening updateById(Long id, Screening screening);
    Screening patchById(Long id, Screening screening);
    void deleteById(Long id);

}
