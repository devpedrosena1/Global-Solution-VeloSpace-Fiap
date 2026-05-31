package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadPriority;

import br.com.fiap.javaadv.VeloSpace.model.PayloadPriority;
import lombok.Builder;

@Builder
public record PayloadPriorityResponseDTO(
        Long payloadPriorityId,
        Long priorityLevel,
        String description) {

    public static PayloadPriorityResponseDTO from(PayloadPriority payloadPriority) {
        if (payloadPriority == null) {
            return null;
        }
        return PayloadPriorityResponseDTO.builder()
                .payloadPriorityId(payloadPriority.getPayloadPriorityId())
                .priorityLevel(payloadPriority.getPriority_level())
                .description(payloadPriority.getDescription())
                .build();
    }
}