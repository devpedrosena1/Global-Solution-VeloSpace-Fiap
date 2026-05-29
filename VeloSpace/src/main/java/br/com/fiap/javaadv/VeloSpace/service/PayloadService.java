package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.dto.PayloadApprovalDTO;

public interface PayloadService {

    Payload findById(Long id);
    Payload create(Payload payload);
    Payload updateById(Long id, Payload payload);
    Payload patchById(Long id, Payload payload);
    Payload patchApprovalById(Long id, PayloadApprovalDTO dto);
    Payload patchTrackingById(Long id, Payload payload);
    void deleteById(Long id);

}
