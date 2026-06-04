package br.com.fiap.javaadv.VeloSpace.service.Satellite;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.Role;
import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.SatelliteSortField;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.BusinessRuleException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Satellite;
import br.com.fiap.javaadv.VeloSpace.model.Operator;
import br.com.fiap.javaadv.VeloSpace.model.SatelliteStatus;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.SatelliteRepository;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProvider.LaunchProviderService;
import br.com.fiap.javaadv.VeloSpace.service.Operator.OperatorService;
import br.com.fiap.javaadv.VeloSpace.service.SatelliteStatus.SatelliteStatusService;
import br.com.fiap.javaadv.VeloSpace.service.Shipper.ShipperService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SatelliteServiceImpl implements SatelliteService<Satellite, Long> {

    private final SatelliteRepository satelliteRepository;

    private final ShipperService<Shipper, Long> shipperService;

    private final OperatorService<Operator, Long> operatorService;

    private final LaunchProviderService<LaunchProvider, Long> launchProviderService;

    private final SatelliteStatusService<SatelliteStatus, Long> satelliteStatusService;

    private void validateShipperOwner(JwtUserData authUser, Satellite satellite) {
        Long shipperUserAccountId = satellite.getShipper().getUserAccount().getUserAccountId();

        if (!Objects.equals(authUser.userId(), shipperUserAccountId)) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar este satélite.");
        }
    }

    private void validateOperatorRelated(JwtUserData authUser, Satellite satellite) {
        Operator operator = operatorService.findByIdOrThrow(authUser.userId());

        if (!Objects.equals(operator.getLaunchProvider(), satellite.getLaunchProvider())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar este satélite.");
        }
    }

    private void validateAccess(JwtUserData authUser, Satellite satellite) {
        if (authUser.role().equals(Role.SHIPPER)) {
            validateShipperOwner(authUser, satellite);
            return;
        }

        if (authUser.role().equals(Role.OPERATOR)) {
            validateOperatorRelated(authUser, satellite);
            return;
        }

        throw new ForbiddenException(
                "Você não possui permissão para acessar este satélite.");
    }

    private void validateCurrentStatus(Satellite satellite, String expectedStatusCode, String errorMessage) {
        String currentStatusCode = satellite.getSatelliteStatus().getCode();

        if (!currentStatusCode.equals(expectedStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    private void validateCurrentStatusIn(Satellite satellite, List<String> allowedStatusCodes, String errorMessage) {
        String currentStatusCode = satellite.getSatelliteStatus().getCode();

        if (!allowedStatusCodes.contains(currentStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    @Override
    public Satellite findByIdOrThrow(Long id) {
        return satelliteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Satélite não encontrado."));
    }

    @Override
    public Satellite findById(Long id, JwtUserData authUser) {
        Satellite satellite = findByIdOrThrow(id);
        validateAccess(authUser, satellite);
        return satellite;
    }

    @Override
    public Page<Satellite> findAllByLaunchProviderId(
            Long id,
            int page,
            int items,
            SatelliteSortField sortBy,
            String direction,
            JwtUserData authUser) {

        launchProviderService.findById(id, authUser);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy.name()).descending()
                : Sort.by(sortBy.name()).ascending();

        return satelliteRepository.findByLaunchProvider_LaunchProviderId(id, PageRequest.of(page, items, sort));
    }

    @Override
    public Satellite create(Satellite satellite, JwtUserData authUser) {
        Shipper shipper = shipperService.findByUserAccountIdOrThrow(authUser.userId());

        LaunchProvider launchProvider = launchProviderService.findByIdOrThrow(
                satellite.getLaunchProvider().getLaunchProviderId());

        SatelliteStatus status = satelliteStatusService.getRequiredByCode("PENDING_APPROVAL");

        satellite.setShipper(shipper);
        satellite.setLaunchProvider(launchProvider);
        satellite.setSatelliteStatus(status);

        return satelliteRepository.save(satellite);
    }

    @Override
    public Satellite updateById(Long id, Satellite satellite, JwtUserData authUser) {
        Satellite existing = findByIdOrThrow(id);

        validateShipperOwner(authUser, existing);

        validateCurrentStatus(
                existing,
                "PENDING_APPROVAL",
                "Só é possível alterar um satélite que está aguardando aprovação.");

        LaunchProvider launchProvider = launchProviderService.findByIdOrThrow(
                satellite.getLaunchProvider().getLaunchProviderId());

        existing.setName(satellite.getName());
        existing.setHeight(satellite.getHeight());
        existing.setWidth(satellite.getWidth());
        existing.setLength(satellite.getLength());
        existing.setWeight(satellite.getWeight());
        existing.setLaunchJustification(satellite.getLaunchJustification());
        existing.setLaunchProvider(launchProvider);

        return satelliteRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        Satellite satellite = findByIdOrThrow(id);

        validateShipperOwner(authUser, satellite);

        validateCurrentStatusIn(
                satellite,
                List.of(
                        "PENDING_APPROVAL",
                        "REJECTED",
                        "AWAITING_SHIPMENT",
                        "INSPECTION_REJECTED"),
                "Não é possível excluir um satélite neste status.");

        satelliteRepository.delete(satellite);
    }

}
