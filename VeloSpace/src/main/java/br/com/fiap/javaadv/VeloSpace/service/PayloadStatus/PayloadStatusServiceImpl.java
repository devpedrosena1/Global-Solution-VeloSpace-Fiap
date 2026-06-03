package br.com.fiap.javaadv.VeloSpace.service.PayloadStatus;

import org.springframework.stereotype.Service;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.model.PayloadStatus;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayloadStatusServiceImpl implements PayloadStatusService<PayloadStatus, Long> {

    private final PayloadStatusRepository payloadStatusRepository;

    @Override
    public PayloadStatus findByCode(String code) {
        return payloadStatusRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(
                        "Status da carga não encontrado."));
    }

    @Override
    public PayloadStatus getRequiredByCode(String code) {
        return payloadStatusRepository.findByCode(code)
                .orElseThrow(() -> new IllegalStateException(
                        "Status de carga obrigatório não encontrado na base de dados: "
                                + code + "."));
    }

}
