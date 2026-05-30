package br.com.fiap.javaadv.VeloSpace.service.Payload;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadStatusRepository;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadApprovalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayloadServiceImpl implements PayloadService<Payload, Long> {

    private final PayloadRepository payloadRepository;

    private final PayloadStatusRepository payloadStatusRepository;

    @Override
    public Payload findById(Long id) {
        return payloadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload create(Payload payload) {
        return payloadRepository.save(payload);
    }

    @Override
    public Payload updateById(Long id, Payload payload) {
        return payloadRepository.findById(id)
                .map(existing -> {
                    existing.setHeight(payload.getHeight());
                    existing.setWidth(payload.getWidth());
                    existing.setLength(payload.getLength());
                    existing.setWeight(payload.getWeight());
                    existing.setTrackingCode(payload.getTrackingCode());
                    existing.setPayloadStatus(payload.getPayloadStatus());
                    existing.setShipper(payload.getShipper());
                    existing.setLaunchJustification(payload.getLaunchJustification());
                    existing.setPayloadPriority(payload.getPayloadPriority());
                    existing.setLaunchProvider(payload.getLaunchProvider());
                    return payloadRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload patchById(Long id, Payload payload) {
        return payloadRepository.findById(id)
                .map(existing -> {
                    if (payload.getHeight() != 0)
                        existing.setHeight(payload.getHeight());
                    if (payload.getWidth() != 0)
                        existing.setWidth(payload.getWidth());
                    if (payload.getLength() != 0)
                        existing.setLength(payload.getLength());
                    if (payload.getWeight() != 0)
                        existing.setWeight(payload.getWeight());
                    if (payload.getTrackingCode() != null)
                        existing.setTrackingCode(payload.getTrackingCode());
                    if (payload.getPayloadStatus() != null)
                        existing.setPayloadStatus(payload.getPayloadStatus());
                    if (payload.getShipper() != null)
                        existing.setShipper(payload.getShipper());
                    if (payload.getLaunchJustification() != null)
                        existing.setLaunchJustification(payload.getLaunchJustification());
                    if (payload.getPayloadPriority() != null)
                        existing.setPayloadPriority(payload.getPayloadPriority());
                    if (payload.getLaunchProvider() != null)
                        existing.setLaunchProvider(payload.getLaunchProvider());
                    return payloadRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    /*
     * Esse método aqui eu usei o Claude para auxiliar, tanto que ele pediu
     * para criar um DTO específico só para isso, quero entender como
     * voce quer que esse método seja feito de fato
     */

    @Override
    public Payload patchApprovalById(Long id, PayloadApprovalDTO dto) {
        return payloadRepository.findById(id)
                .map(existing -> {
                    existing.setPayloadStatus(payloadStatusRepository.findByDescription(dto.getStatus())
                            .orElseThrow(() -> new RuntimeException("Status not found: " + dto.getStatus())));
                    return payloadRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload patchTrackingById(Long id, Payload payload) {
        return payloadRepository.findById(id)
                .map(existing -> {
                    if (payload.getTrackingCode() != null)
                        existing.setTrackingCode(payload.getTrackingCode());
                    return payloadRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        payloadRepository.deleteById(id);
    }

}
