package br.com.fiap.javaadv.VeloSpace.service.PayloadHandler;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.Role;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.BusinessRuleException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProvider.LaunchProviderService;
import br.com.fiap.javaadv.VeloSpace.service.PayloadHandlerStatus.PayloadHandlerStatusService;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PayloadHandlerServiceImpl implements PayloadHandlerService<PayloadHandler, Long> {

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final PayloadHandlerStatusService<PayloadHandlerStatus, Long> payloadHandlerStatusService;

    private final LaunchProviderService<LaunchProvider, Long> launchProviderService;

    private final UserValidationService userValidationService;

    private final PasswordEncoder passwordEncoder;

    private void validateLaunchProviderOwner(JwtUserData authUser, PayloadHandler payloadHandler) {
        Long launchProviderId = payloadHandler.getLaunchProvider().getLaunchProviderId();

        if (!Objects.equals(authUser.userId(), launchProviderId)) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar este operador de carga.");
        }
    }

    private void validatePayloadHandlerOwner(JwtUserData authUser, PayloadHandler payloadHandler) {
        if (!Objects.equals(authUser.userId(), payloadHandler.getPayloadHandlerId())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar este operador de carga.");
        }
    }

    private void validateAccess(JwtUserData authUser, PayloadHandler payloadHandler) {
        if (authUser.role().equals(Role.LAUNCH_PROVIDER)) {
            validateLaunchProviderOwner(authUser, payloadHandler);
            return;
        }

        if (authUser.role().equals(Role.PAYLOAD_HANDLER)) {
            validatePayloadHandlerOwner(authUser, payloadHandler);
            return;
        }

        throw new ForbiddenException(
                "Você não possui permissão para acessar este operador de carga.");
    }

    private void validateCurrentStatus(
            PayloadHandler payloadHandler,
            String expectedStatusCode,
            String errorMessage) {

        String currentStatusCode = payloadHandler.getPayloadHandlerStatus().getCode();

        if (!currentStatusCode.equals(expectedStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    private void changeStatus(PayloadHandler payloadHandler, String statusCode) {
        PayloadHandlerStatus status = payloadHandlerStatusService.getRequiredByCode(statusCode);
        payloadHandler.setPayloadHandlerStatus(status);
        payloadHandlerRepository.save(payloadHandler);
    }

    @Override
    public PayloadHandler findByIdOrThrow(Long id) {
        return payloadHandlerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Operador de carga não encontrado."));
    }

    @Override
    public PayloadHandler findById(Long id, JwtUserData authUser) {
        PayloadHandler payloadHandler = findByIdOrThrow(id);
        validateAccess(authUser, payloadHandler);
        return payloadHandler;
    }

    @Override
    public PayloadHandler create(PayloadHandler payloadHandler) {
        payloadHandlerRepository.findByCpf(payloadHandler.getCpf())
                .ifPresent(other -> {
                    throw new FieldValidationException(
                            "cpf",
                            "Este CPF já está em uso.");
                });

        launchProviderService.findByIdOrThrow(
                payloadHandler.getLaunchProvider().getLaunchProviderId());

        userValidationService.validUniqueEmail(payloadHandler.getEmail());

        PayloadHandlerStatus status = payloadHandlerStatusService.getRequiredByCode("PENDING_APPROVAL");

        payloadHandler.setPayloadHandlerStatus(status);
        payloadHandler.setHashedPassword(
                passwordEncoder.encode(payloadHandler.getHashedPassword()));

        return payloadHandlerRepository.save(payloadHandler);
    }

    @Override
    public PayloadHandler updateById(Long id, PayloadHandler payloadHandler, JwtUserData authUser) {
        PayloadHandler existing = findByIdOrThrow(id);

        validatePayloadHandlerOwner(authUser, existing);

        if (!passwordEncoder.matches(
                payloadHandler.getHashedPassword(),
                existing.getHashedPassword())) {

            throw new FieldValidationException(
                    "password",
                    "Senha atual incorreta.");
        }

        if (!payloadHandler.getCpf().equals(existing.getCpf())) {
            payloadHandlerRepository.findByCpf(payloadHandler.getCpf())
                    .ifPresent(other -> {
                        throw new FieldValidationException(
                                "cpf",
                                "Este CPF já está em uso.");
                    });
        }

        if (!payloadHandler.getEmail().equals(existing.getEmail())) {
            userValidationService.validUniqueEmail(payloadHandler.getEmail());
        }

        launchProviderService.findByIdOrThrow(
                payloadHandler.getLaunchProvider().getLaunchProviderId());

        existing.setName(payloadHandler.getName());
        existing.setCpf(payloadHandler.getCpf());
        existing.setLaunchProvider(payloadHandler.getLaunchProvider());
        existing.setEmail(payloadHandler.getEmail());
        existing.setPhone(payloadHandler.getPhone());

        return payloadHandlerRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        PayloadHandler payloadHandler = findByIdOrThrow(id);
        validatePayloadHandlerOwner(authUser, payloadHandler);
        payloadHandlerRepository.delete(payloadHandler);
    }

    @Override
    public void approval(Long id, boolean approved, JwtUserData authUser) {
        PayloadHandler payloadHandler = findByIdOrThrow(id);

        validateLaunchProviderOwner(authUser, payloadHandler);

        validateCurrentStatus(
                payloadHandler,
                "PENDING_APPROVAL",
                "Só é possível aprovar ou reprovar um operador de carga que está aguardando aprovação.");

        changeStatus(payloadHandler, approved ? "APPROVED" : "REJECTED");
    }

    @Override
    public void reapply(Long id, JwtUserData authUser) {
        PayloadHandler payloadHandler = findByIdOrThrow(id);

        validatePayloadHandlerOwner(authUser, payloadHandler);

        validateCurrentStatus(
                payloadHandler,
                "REJECTED",
                "Só é possível reaplicar um operador de carga que está rejeitado.");

        changeStatus(payloadHandler, "PENDING_APPROVAL");
    }

}
