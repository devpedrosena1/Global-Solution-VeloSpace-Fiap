package br.com.fiap.javaadv.VeloSpace.service.Payload;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.Role;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.BusinessRuleException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.PayloadStatus;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProvider.LaunchProviderService;
import br.com.fiap.javaadv.VeloSpace.service.PayloadHandler.PayloadHandlerService;
import br.com.fiap.javaadv.VeloSpace.service.PayloadStatus.PayloadStatusService;
import br.com.fiap.javaadv.VeloSpace.service.Shipper.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PayloadServiceImpl implements PayloadService<Payload, Long> {

    private final PayloadRepository payloadRepository;

    private final ShipperService<Shipper, Long> shipperService;

    private final PayloadHandlerService<PayloadHandler, Long> payloadHandlerService;

    private final LaunchProviderService<LaunchProvider, Long> launchProviderService;

    private final PayloadStatusService<PayloadStatus, Long> payloadStatusService;

    private void validateShipperOwner(JwtUserData authUser, Payload payload) {
        Long shipperId = payload.getShipper().getShipperId();

        if (!Objects.equals(authUser.userId(), shipperId)) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar esta carga.");
        }
    }

    private void validatePayloadHandlerRelated(JwtUserData authUser, Payload payload) {
        PayloadHandler payloadHandler = payloadHandlerService.findByIdOrThrow(authUser.userId());

        if (!Objects.equals(payloadHandler.getLaunchProvider(), payload.getLaunchProvider())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar esta carga.");
        }
    }

    private void validateAccess(JwtUserData authUser, Payload payload) {
        if (authUser.role().equals(Role.SHIPPER)) {
            validateShipperOwner(authUser, payload);
            return;
        }

        if (authUser.role().equals(Role.PAYLOAD_HANDLER)) {
            validatePayloadHandlerRelated(authUser, payload);
            return;
        }

        throw new ForbiddenException(
                "Você não possui permissão para acessar esta carga.");
    }

    private void validateCurrentStatus(Payload payload, String expectedStatusCode, String errorMessage) {
        String currentStatusCode = payload.getPayloadStatus().getCode();

        if (!currentStatusCode.equals(expectedStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    private void validateCurrentStatusIn(Payload payload, List<String> allowedStatusCodes, String errorMessage) {
        String currentStatusCode = payload.getPayloadStatus().getCode();

        if (!allowedStatusCodes.contains(currentStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    @Override
    public Payload findByIdOrThrow(Long id) {
        return payloadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Carga não encontrada."));
    }

    @Override
    public Payload findById(Long id, JwtUserData authUser) {
        Payload payload = findByIdOrThrow(id);
        validateAccess(authUser, payload);
        return payload;
    }

    @Override
    public Payload create(Payload payload, JwtUserData authUser) {
        Shipper shipper = shipperService.findByIdOrThrow(authUser.userId());

        LaunchProvider launchProvider = launchProviderService.findByIdOrThrow(
                payload.getLaunchProvider().getLaunchProviderId());

        PayloadStatus status = payloadStatusService.getRequiredByCode("PENDING_APPROVAL");

        payload.setShipper(shipper);
        payload.setLaunchProvider(launchProvider);
        payload.setPayloadStatus(status);

        return payloadRepository.save(payload);
    }

    @Override
    public Payload updateById(Long id, Payload payload, JwtUserData authUser) {
        Payload existing = findByIdOrThrow(id);

        validateShipperOwner(authUser, existing);

        validateCurrentStatus(
                existing,
                "PENDING_APPROVAL",
                "Só é possível alterar uma carga que está aguardando aprovação.");

        LaunchProvider launchProvider = launchProviderService.findByIdOrThrow(
                payload.getLaunchProvider().getLaunchProviderId());

        existing.setName(payload.getName());
        existing.setHeight(payload.getHeight());
        existing.setWidth(payload.getWidth());
        existing.setLength(payload.getLength());
        existing.setWeight(payload.getWeight());
        existing.setLaunchJustification(payload.getLaunchJustification());
        existing.setLaunchProvider(launchProvider);

        return payloadRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        Payload payload = findByIdOrThrow(id);

        validateShipperOwner(authUser, payload);

        validateCurrentStatusIn(
                payload,
                List.of(
                        "PENDING_APPROVAL",
                        "REJECTED",
                        "AWAITING_SHIPMENT",
                        "SCREENING_REJECTED"),
                "Não é possível excluir uma carga neste status.");

        payloadRepository.delete(payload);
    }

}
