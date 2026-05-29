package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandlerStatus;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;
import lombok.Builder;

@Builder
public record PayloadHandlerStatusResponseDTO(
        Long payloadHandlerStatusId,
        String code,
        String description) {

    public static PayloadHandlerStatusResponseDTO from(
            PayloadHandlerStatus payloadHandlerStatus) {

        if (payloadHandlerStatus == null) {
            return null;
        }

        return PayloadHandlerStatusResponseDTO.builder()
                .payloadHandlerStatusId(payloadHandlerStatus.getPayloadHandlerStatusId())
                .code(payloadHandlerStatus.getCode())
                .description(payloadHandlerStatus.getDescription())
                .build();
    }

}
