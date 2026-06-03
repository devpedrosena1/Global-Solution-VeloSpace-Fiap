package br.com.fiap.javaadv.VeloSpace.service.PayloadHandlerStatus;

import org.springframework.stereotype.Service;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayloadHandlerStatusServiceImpl implements PayloadHandlerStatusService<PayloadHandlerStatus, Long> {

    private final PayloadHandlerStatusRepository payloadHandlerStatusRepository;

    @Override
    public PayloadHandlerStatus findByCode(String code) {
        return payloadHandlerStatusRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(
                        "Status de operador de carga não encontrado."));
    }

    @Override
    public PayloadHandlerStatus getRequiredByCode(String code) {
        return payloadHandlerStatusRepository.findByCode(code)
                .orElseThrow(() -> new IllegalStateException(
                        "Status de operador de carga obrigatório não encontrado na base de dados: " + code + "."));
    }

}
