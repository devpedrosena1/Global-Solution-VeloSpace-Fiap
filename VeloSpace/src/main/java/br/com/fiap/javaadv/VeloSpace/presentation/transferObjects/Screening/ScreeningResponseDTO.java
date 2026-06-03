package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.ScreeningResult;
import br.com.fiap.javaadv.VeloSpace.model.Screening;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScreeningResponseDTO(
        Long screeningId,
        Long payloadId,
        Long payloadHandlerId,
        int measuredHeight,
        int measuredWidth,
        int measuredLength,
        int measuredWeight,
        ScreeningResult result,
        LocalDateTime inspectionDate) {

    public static ScreeningResponseDTO from(Screening screening) {
        if (screening == null) {
            return null;
        }

        return ScreeningResponseDTO.builder()
                .screeningId(screening.getScreeningId())
                .payloadId(screening.getPayload().getPayloadId())
                .payloadHandlerId(screening.getPayloadHandler().getPayloadHandlerId())
                .measuredHeight(screening.getMeasuredHeight())
                .measuredWidth(screening.getMeasuredWidth())
                .measuredLength(screening.getMeasuredLength())
                .measuredWeight(screening.getMeasuredWeight())
                .result(screening.getResult())
                .inspectionDate(screening.getInspectionDate())
                .build();
    }

}
