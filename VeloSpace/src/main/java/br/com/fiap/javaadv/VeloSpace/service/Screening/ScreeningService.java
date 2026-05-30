package br.com.fiap.javaadv.VeloSpace.service.Screening;

import br.com.fiap.javaadv.VeloSpace.model.Screening;

public interface ScreeningService<T, ID> {

    Screening findById(ID id);

    Screening create(T o);

    Screening updateById(ID id, T o);

    Screening patchById(ID id, T o);

    void deleteById(ID id);

}
