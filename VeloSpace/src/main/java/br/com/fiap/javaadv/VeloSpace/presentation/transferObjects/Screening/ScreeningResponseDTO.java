package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening;

import br.com.fiap.javaadv.VeloSpace.model.Screening;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScreeningResponseDTO(
        Long screeningId,
        Long payloadId,
        String payloadName,
        Long payloadHandlerId,
        String payloadHandlerName,
        int measuredHeight,
        int measuredWidth,
        int measureLength,
        int measuredWeight,
        LocalDateTime inspectionDate) {

    public static ScreeningResponseDTO from(Screening screening) {
        if (screening == null) {
            return null;
        }
        return ScreeningResponseDTO.builder()
                .screeningId(screening.getScreeningId())
                .payloadId(screening.getPayload().getPayloadId())
                .payloadName(screening.getPayload().getName())
                .payloadHandlerId(screening.getPayloadHandler().getPayloadHandlerId())
                .payloadHandlerName(screening.getPayloadHandler().getName())
                .measuredHeight(screening.getMeasuredHeight())
                .measuredWidth(screening.getMeasuredWidth())
                .measureLength(screening.getMeasureLength())
                .measuredWeight(screening.getMeasuredWeight())
                .inspectionDate(screening.getInspectionDate())
                .build();
    }
}