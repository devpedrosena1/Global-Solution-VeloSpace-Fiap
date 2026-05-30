package br.com.fiap.javaadv.VeloSpace.service.PayloadHandler;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayloadHandlerServiceImpl implements PayloadHandlerService<PayloadHandler, Long> {

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final ShipperRepository shipperRepository;

    private final LaunchProviderRepository launchProviderRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public PayloadHandler findMe() {
        // TO-DO: podemos implementar depois quando tiver security
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PayloadHandler findById(Long id) {
        return payloadHandlerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler create(PayloadHandler payloadHandler) {
        shipperRepository.findByEmail(payloadHandler.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        payloadHandlerRepository.findByEmail(payloadHandler.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        launchProviderRepository.findByEmail(payloadHandler.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        payloadHandler.setHashedPassword(passwordEncoder.encode(payloadHandler.getHashedPassword()));
        return payloadHandlerRepository.save(payloadHandler);
    }

    @Override
    public PayloadHandler updateById(Long id, PayloadHandler payloadHandler) {
        return payloadHandlerRepository.findById(id)
                .map(existing -> {
                    existing.setName(payloadHandler.getName());
                    existing.setCpf(payloadHandler.getCpf());
                    existing.setPayloadHandlerStatus(payloadHandler.getPayloadHandlerStatus());
                    existing.setLaunchProvider(payloadHandler.getLaunchProvider());
                    existing.setEmail(payloadHandler.getEmail());
                    existing.setPhone(payloadHandler.getPhone());
                    existing.setHashedPassword(payloadHandler.getHashedPassword());
                    return payloadHandlerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler patchById(Long id, PayloadHandler payloadHandler) {
        return payloadHandlerRepository.findById(id)
                .map(existing -> {
                    if (payloadHandler.getName() != null)
                        existing.setName(payloadHandler.getName());
                    if (payloadHandler.getCpf() != null)
                        existing.setCpf(payloadHandler.getCpf());
                    if (payloadHandler.getPayloadHandlerStatus() != null)
                        existing.setPayloadHandlerStatus(payloadHandler.getPayloadHandlerStatus());
                    if (payloadHandler.getLaunchProvider() != null)
                        existing.setLaunchProvider(payloadHandler.getLaunchProvider());
                    if (payloadHandler.getEmail() != null)
                        existing.setEmail(payloadHandler.getEmail());
                    if (payloadHandler.getPhone() != null)
                        existing.setPhone(payloadHandler.getPhone());
                    if (payloadHandler.getHashedPassword() != null)
                        existing.setHashedPassword(payloadHandler.getHashedPassword());
                    return payloadHandlerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public PayloadHandler patchPasswordById(Long id, PayloadHandler payloadHandler) {
        return payloadHandlerRepository.findById(id)
                .map(existing -> {
                    if (payloadHandler.getHashedPassword() != null)
                        existing.setHashedPassword(payloadHandler.getHashedPassword());
                    return payloadHandlerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PayloadHandler not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        payloadHandlerRepository.deleteById(id);
    }

}
