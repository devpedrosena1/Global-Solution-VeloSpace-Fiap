package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import lombok.Builder;

@Builder
public record LaunchProviderResponseDTO(
        Long launchProviderId,
        String corporateName,
        String cnpj,
        String email,
        Long phone) {

    public static LaunchProviderResponseDTO from(
            LaunchProvider launchProvider) {

        if (launchProvider == null) {
            return null;
        }

        return LaunchProviderResponseDTO.builder()
                .launchProviderId(launchProvider.getLaunchProviderId())
                .corporateName(launchProvider.getCorporateName())
                .cnpj(launchProvider.getCnpj())
                .email(launchProvider.getEmail())
                .phone(launchProvider.getPhone())
                .build();
    }

}
