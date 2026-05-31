package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadPriority.PayloadPriorityResponseDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadStatus.PayloadStatusResponseDTO;
import lombok.Builder;

@Builder
public record PayloadResponseDTO(
        Long payloadId,
        Long shipperId,
        String shipperName,
        Long launchProviderId,
        String launchProviderCorporateName,
        String name,
        int height,
        int width,
        int length,
        int weight,
        String launchJustification,
        PayloadStatusResponseDTO status,
        PayloadPriorityResponseDTO priority,
        String trackingCode) {

    public static PayloadResponseDTO from(Payload payload) {
        if (payload == null) {
            return null;
        }
        return PayloadResponseDTO.builder()
                .payloadId(payload.getPayloadId())
                .shipperId(payload.getShipper().getShipperId())
                .shipperName(payload.getShipper().getName())
                .launchProviderId(payload.getLaunchProvider().getLaunchProviderId())
                .launchProviderCorporateName(payload.getLaunchProvider().getCorporateName())
                .name(payload.getName())
                .height(payload.getHeight())
                .width(payload.getWidth())
                .length(payload.getLength())
                .weight(payload.getWeight())
                .launchJustification(payload.getLaunchJustification())
                .status(PayloadStatusResponseDTO.from(payload.getPayloadStatus()))
                .priority(PayloadPriorityResponseDTO.from(payload.getPayloadPriority()))
                .trackingCode(payload.getTrackingCode())
                .build();
    }
}