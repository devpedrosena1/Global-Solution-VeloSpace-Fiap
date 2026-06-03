package br.com.fiap.javaadv.VeloSpace.service.Screening;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.ScreeningResult;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.BusinessRuleException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.PayloadStatus;
import br.com.fiap.javaadv.VeloSpace.model.Screening;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ScreeningRepository;
import br.com.fiap.javaadv.VeloSpace.service.Payload.PayloadService;
import br.com.fiap.javaadv.VeloSpace.service.PayloadHandler.PayloadHandlerService;
import br.com.fiap.javaadv.VeloSpace.service.PayloadStatus.PayloadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService<Screening, Long> {

    private final ScreeningRepository screeningRepository;

    private final PayloadRepository payloadRepository;

    private final PayloadHandlerService<PayloadHandler, Long> payloadHandlerService;

    private final PayloadService<Payload, Long> payloadService;

    private final PayloadStatusService<PayloadStatus, Long> payloadStatusService;

    private Screening getScreeningOrThrow(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Triagem não encontrada."));
    }

    private void validatePayloadHandlerRelated(JwtUserData authUser, Payload payload) {
        PayloadHandler payloadHandler = payloadHandlerService.findByIdOrThrow(authUser.userId());

        if (!Objects.equals(payloadHandler.getLaunchProvider(), payload.getLaunchProvider())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar esta triagem.");
        }
    }

    private void validateCurrentPayloadStatus(
            Payload payload,
            String expectedStatusCode,
            String errorMessage) {

        String currentStatusCode = payload.getPayloadStatus().getCode();

        if (!currentStatusCode.equals(expectedStatusCode)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    private boolean exceedsTolerance(Integer declaredValue, Integer measuredValue) {
        double maximumAllowedDifference = declaredValue * 0.05;
        return Math.abs(measuredValue - declaredValue) > maximumAllowedDifference;
    }

    private boolean shouldRejectPayload(Screening screening, Payload payload) {
        return exceedsTolerance(payload.getHeight(), screening.getMeasuredHeight())
                || exceedsTolerance(payload.getWidth(), screening.getMeasuredWidth())
                || exceedsTolerance(payload.getLength(), screening.getMeasuredLength())
                || exceedsTolerance(payload.getWeight(), screening.getMeasuredWeight());
    }

    private void updatePayloadStatusAfterScreening(Payload payload, String nextStatusCode) {
        PayloadStatus nextStatus = payloadStatusService.getRequiredByCode(nextStatusCode);

        payload.setPayloadStatus(nextStatus);
        payloadRepository.save(payload);
    }

    @Override
    public Screening findById(Long id, JwtUserData authUser) {
        Screening screening = getScreeningOrThrow(id);
        Payload payload = screening.getPayload();

        validatePayloadHandlerRelated(authUser, payload);

        return screening;
    }

    @Override
    public Screening create(Screening screening, JwtUserData authUser) {
        Payload payload = payloadService.findByIdOrThrow(
                screening.getPayload().getPayloadId());

        validatePayloadHandlerRelated(authUser, payload);

        validateCurrentPayloadStatus(
                payload,
                "PENDING_SCREENING",
                "Só é possível realizar triagem de uma carga que está aguardando triagem.");

        screening.setPayload(payload);
        screening.setInspectionDate(LocalDateTime.now());

        boolean screeningResult = shouldRejectPayload(screening, payload);

        screening.setResult(screeningResult ? ScreeningResult.A : ScreeningResult.R);

        Screening savedScreening = screeningRepository.save(screening);

        updatePayloadStatusAfterScreening(payload, screeningResult
                ? "SCREENING_REJECTED"
                : "READY_FOR_LAUNCH");

        return savedScreening;
    }

}
