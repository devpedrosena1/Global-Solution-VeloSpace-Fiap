package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadStatusRepository;
import br.com.fiap.javaadv.VeloSpace.presentation.dto.PayloadApprovalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayloadServiceImpl implements PayloadService{

    private final PayloadRepository repository;
    private final PayloadStatusRepository payloadStatusRepository;

    @Override
    public Payload findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload create(Payload payload) {
        return repository.save(payload);
    }

    @Override
    public Payload updateById(Long id, Payload payload) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setHeight(payload.getHeight());
                    existing.setWidth(payload.getWidth());
                    existing.setDepth(payload.getDepth());
                    existing.setWeight(payload.getWeight());
                    existing.setTrackingCode(payload.getTrackingCode());
                    existing.setPayloadStatus(payload.getPayloadStatus());
                    existing.setShipper(payload.getShipper());
                    existing.setRocket(payload.getRocket());
                    existing.setJustification(payload.getJustification());
                    existing.setPayloadPriority(payload.getPayloadPriority());
                    existing.setLaunchProvider(payload.getLaunchProvider());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload patchById(Long id, Payload payload) {
        return repository.findById(id)
                .map(existing -> {
                    if (payload.getHeight() != 0) existing.setHeight(payload.getHeight());
                    if (payload.getWidth() != 0) existing.setWidth(payload.getWidth());
                    if (payload.getDepth() != 0) existing.setDepth(payload.getDepth());
                    if (payload.getWeight() != 0) existing.setWeight(payload.getWeight());
                    if (payload.getTrackingCode() != null) existing.setTrackingCode(payload.getTrackingCode());
                    if (payload.getPayloadStatus() != null) existing.setPayloadStatus(payload.getPayloadStatus());
                    if (payload.getShipper() != null) existing.setShipper(payload.getShipper());
                    if (payload.getRocket() != null) existing.setRocket(payload.getRocket());
                    if (payload.getJustification() != null) existing.setJustification(payload.getJustification());
                    if (payload.getPayloadPriority() != null) existing.setPayloadPriority(payload.getPayloadPriority());
                    if (payload.getLaunchProvider() != null) existing.setLaunchProvider(payload.getLaunchProvider());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    /*
    * Esse metodo aqui eu usei o Claude para auxiliar, tanto que ele pediu
    * para criar um DTO específico só para isso, quero entender como
    * voce quer que esse metodo seja feito de fato
    * */

    @Override
    public Payload patchApprovalById(Long id, PayloadApprovalDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    if ("REJECTED".equalsIgnoreCase(dto.getStatus()) && dto.getJustification() == null) {
                        throw new RuntimeException("Justification is required when rejecting a payload");
                    }
                    existing.setPayloadStatus(payloadStatusRepository.findByDescription(dto.getStatus())
                            .orElseThrow(() -> new RuntimeException("Status not found: " + dto.getStatus())));
                    if (dto.getJustification() != null) existing.setJustification(dto.getJustification());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public Payload patchTrackingById(Long id, Payload payload) {
        return repository.findById(id)
                .map(existing -> {
                    if (payload.getTrackingCode() != null) existing.setTrackingCode(payload.getTrackingCode());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payload not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
