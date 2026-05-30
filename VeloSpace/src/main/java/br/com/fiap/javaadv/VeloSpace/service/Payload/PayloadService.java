package br.com.fiap.javaadv.VeloSpace.service.Payload;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadApprovalDTO;

public interface PayloadService<T, ID> {

    Payload findById(ID id);

    Payload create(T o);

    Payload updateById(ID id, T o);

    Payload patchById(ID id, T o);

    Payload patchApprovalById(ID id, PayloadApprovalDTO dto);

    Payload patchTrackingById(ID id, T o);

    void deleteById(ID id);

}
