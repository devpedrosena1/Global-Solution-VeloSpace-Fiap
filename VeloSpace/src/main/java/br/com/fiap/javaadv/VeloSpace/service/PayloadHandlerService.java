package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;

public interface PayloadHandlerService {

    PayloadHandler findMe();
    PayloadHandler findById(Long id);
    PayloadHandler create(PayloadHandler payloadHandler);
    PayloadHandler updateById(Long id, PayloadHandler payloadHandler);
    PayloadHandler patchById(Long id, PayloadHandler payloadHandler);
    PayloadHandler patchPasswordById(Long id, PayloadHandler payloadHandler);
    void deleteById(Long id);

}
