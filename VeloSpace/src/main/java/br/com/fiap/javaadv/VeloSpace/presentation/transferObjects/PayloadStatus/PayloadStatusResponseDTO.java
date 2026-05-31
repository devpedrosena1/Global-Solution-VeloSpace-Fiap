package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadStatus;

import br.com.fiap.javaadv.VeloSpace.model.PayloadStatus;
import lombok.Builder;

@Builder
public record PayloadStatusResponseDTO(
        Long payloadStatusId,
        String code,
        String description) {

    public static PayloadStatusResponseDTO from(PayloadStatus payloadStatus) {
        if (payloadStatus == null) {
            return null;
        }
        return PayloadStatusResponseDTO.builder()
                .payloadStatusId(payloadStatus.getPayloadStatusId())
                .code(payloadStatus.getCode())
                .description(payloadStatus.getDescription())
                .build();
    }
}