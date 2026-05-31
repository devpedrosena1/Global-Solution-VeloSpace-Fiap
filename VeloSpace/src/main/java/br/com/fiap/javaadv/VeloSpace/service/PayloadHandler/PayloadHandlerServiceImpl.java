package br.com.fiap.javaadv.VeloSpace.service.PayloadHandler;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerStatusRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayloadHandlerServiceImpl implements PayloadHandlerService<PayloadHandler, Long> {

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final PayloadHandlerStatusRepository payloadHandlerStatusRepository;

    private final LaunchProviderRepository launchProviderRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserValidationService userValidationService;

    private void validateLaunchProviderOwner(JwtUserData authUser, PayloadHandler payloadHandler) {
        if (!Objects.equals(
                authUser.userId(),
                payloadHandler.getLaunchProvider().getLaunchProviderId())) {
            throw new RuntimeException(
                    "Somente a provedora de lançamento responsável por este operador de carga pode aprová-lo ou reprová-lo.");
        }
    }

    private void validatePayloadHandlerOwner(JwtUserData authUser, PayloadHandler payloadHandler) {
        if (!Objects.equals(
                authUser.userId(),
                payloadHandler.getPayloadHandlerId())) {
            throw new RuntimeException(
                    "Somente o próprio operador de carga pode reaplicar.");
        }
    }

    private void validateCurrentStatus(
            PayloadHandler payloadHandler,
            String expectedStatusCode,
            String errorMessage) {
        if (!payloadHandler.getPayloadHandlerStatus().getCode().equals(expectedStatusCode)) {
            throw new RuntimeException(errorMessage);
        }
    }

    private void updateStatus(PayloadHandler payloadHandler, String statusCode) {
        PayloadHandlerStatus status = payloadHandlerStatusRepository
                .findByCode(statusCode)
                .orElseThrow(() -> new IllegalStateException(
                        "Status não encontrado na base de dados: " + statusCode));

        payloadHandler.setPayloadHandlerStatus(status);
        payloadHandlerRepository.save(payloadHandler);
    }

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
        payloadHandlerRepository.findByCpf(payloadHandler.getCpf())
                .ifPresent(other -> {
                    throw new FieldValidationException("cpf", "Este CPF já está em uso.");
                });

        launchProviderRepository.findById(payloadHandler.getLaunchProvider().getLaunchProviderId())
                .orElseThrow(() -> new FieldValidationException("launchProviderId",
                        "Provedora de lançamento não encontrada."));

        userValidationService.validUniqueEmail(payloadHandler.getEmail());

        PayloadHandlerStatus status = payloadHandlerStatusRepository
                .findByCode("PENDING_APPROVAL")
                .orElseThrow(() -> new IllegalStateException(
                        "Status não encontrado na base de dados: PENDING_APPROVAL."));

        payloadHandler.setPayloadHandlerStatus(status);
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
    public Void approval(JwtUserData authUser, Long id, boolean approval) {
        PayloadHandler payloadHandler = findById(id);

        validateLaunchProviderOwner(authUser, payloadHandler);

        validateCurrentStatus(payloadHandler, "PENDING_APPROVAL",
                "Só é possível aprovar ou reprovar um operador de carga que está aguardando aprovação.");

        updateStatus(payloadHandler, approval ? "APPROVED" : "REJECTED");
        return null;
    }

    @Override
    public Void reapply(JwtUserData authUser, Long id) {
        PayloadHandler payloadHandler = findById(id);

        validatePayloadHandlerOwner(authUser, payloadHandler);

        validateCurrentStatus(payloadHandler, "REJECTED",
                "Só é possível reaplicar um operador de carga que está rejeitado.");

        updateStatus(payloadHandler, "PENDING_APPROVAL");
        return null;
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
