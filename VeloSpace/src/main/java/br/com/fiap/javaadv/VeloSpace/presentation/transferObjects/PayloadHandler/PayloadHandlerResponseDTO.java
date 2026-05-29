package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandlerStatus.PayloadHandlerStatusResponseDTO;
import lombok.Builder;

@Builder
public record PayloadHandlerResponseDTO(
        Long payloadHandlerId,
        Long launchProviderId,
        String launchProviderCorporateName,
        Long cpf,
        String name,
        String email,
        Long phone,
        PayloadHandlerStatusResponseDTO status) {

    public static PayloadHandlerResponseDTO from(
            PayloadHandler payloadHandler) {

        if (payloadHandler == null) {
            return null;
        }

        return PayloadHandlerResponseDTO.builder()
                .payloadHandlerId(payloadHandler.getPayloadHandlerId())
                .launchProviderId(
                        payloadHandler.getLaunchProvider().getLaunchProviderId())
                .launchProviderCorporateName(
                        payloadHandler.getLaunchProvider().getCorporateName())
                .cpf(payloadHandler.getCpf())
                .name(payloadHandler.getName())
                .email(payloadHandler.getEmail())
                .phone(payloadHandler.getPhone())
                .status(
                        PayloadHandlerStatusResponseDTO.from(payloadHandler.getPayloadHandlerStatus()))
                .build();
    }

}
