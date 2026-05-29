package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayloadHandlerServiceImpl implements PayloadHandlerService {

    private final PayloadHandlerRepository repository;

    @Override
    public PayloadHandler findMe() {
        // TODO: podemos implementar depois quando tiver security
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PayloadHandler findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler create(PayloadHandler payloadHandler) {
        return repository.save(payloadHandler);
    }

    @Override
    public PayloadHandler updateById(Long id, PayloadHandler payloadHandler) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(payloadHandler.getName());
                    existing.setCpf(payloadHandler.getCpf());
                    existing.setHandlerStatus(payloadHandler.getHandlerStatus());
                    existing.setLaunchProvider(payloadHandler.getLaunchProvider());
                    existing.setEmail(payloadHandler.getEmail());
                    existing.setPhone(payloadHandler.getPhone());
                    existing.setPasswordHash(payloadHandler.getPasswordHash());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler patchById(Long id, PayloadHandler payloadHandler) {
        return repository.findById(id)
                .map(existing -> {
                    if (payloadHandler.getName() != null) existing.setName(payloadHandler.getName());
                    if (payloadHandler.getCpf() != null) existing.setCpf(payloadHandler.getCpf());
                    if (payloadHandler.getHandlerStatus() != null) existing.setHandlerStatus(payloadHandler.getHandlerStatus());
                    if (payloadHandler.getLaunchProvider() != null) existing.setLaunchProvider(payloadHandler.getLaunchProvider());
                    if (payloadHandler.getEmail() != null) existing.setEmail(payloadHandler.getEmail());
                    if (payloadHandler.getPhone() != null) existing.setPhone(payloadHandler.getPhone());
                    if (payloadHandler.getPasswordHash() != null) existing.setPasswordHash(payloadHandler.getPasswordHash());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler patchPasswordById(Long id, PayloadHandler payloadHandler) {
        return repository.findById(id)
                .map(existing -> {
                    if (payloadHandler.getPasswordHash() != null) existing.setPasswordHash(payloadHandler.getPasswordHash());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
