package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;

public interface PayloadHandlerService<T, ID> {

    PayloadHandler findMe();

    PayloadHandler findById(ID id);

    PayloadHandler create(T o);

    PayloadHandler updateById(ID id, T o);

    PayloadHandler patchById(ID id, T o);

    PayloadHandler patchPasswordById(ID id, T o);

    void deleteById(ID id);

}
