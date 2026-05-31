package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload;

import br.com.fiap.javaadv.VeloSpace.model.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreatePayloadDTO {

    @NotNull(message = "O shipper não pode ser nulo")
    private Long shipperId;

    @NotNull(message = "O provedor de lançamento não pode ser nulo")
    private Long launchProviderId;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 55, message = "O nome deve ter entre 2 e 55 caracteres")
    private String name;

    @NotNull(message = "A altura não pode ser nula")
    @Positive(message = "A altura deve ser maior que zero")
    private Integer height;

    @NotNull(message = "A largura não pode ser nula")
    @Positive(message = "A largura deve ser maior que zero")
    private Integer width;

    @NotNull(message = "O comprimento não pode ser nulo")
    @Positive(message = "O comprimento deve ser maior que zero")
    private Integer length;

    @NotNull(message = "O peso não pode ser nulo")
    @Positive(message = "O peso deve ser maior que zero")
    private Integer weight;

    @NotBlank(message = "A justificativa de lançamento não pode estar em branco")
    @Size(max = 500, message = "A justificativa deve ter no máximo 500 caracteres")
    private String launchJustification;

    @NotNull(message = "A prioridade não pode ser nula")
    private Long payloadPriorityId;

    public static Payload toEntity(CreatePayloadDTO dto) {
        if (dto == null) {
            return null;
        }
        Payload payload = new Payload();
        payload.setShipper(Shipper.builder().shipperId(dto.getShipperId()).build());
        payload.setLaunchProvider(LaunchProvider.builder().launchProviderId(dto.getLaunchProviderId()).build());
        payload.setName(dto.getName());
        payload.setHeight(dto.getHeight());
        payload.setWidth(dto.getWidth());
        payload.setLength(dto.getLength());
        payload.setWeight(dto.getWeight());
        payload.setLaunchJustification(dto.getLaunchJustification());
        payload.setPayloadStatus(PayloadStatus.builder().payloadStatusId(1L).build());
        payload.setPayloadPriority(PayloadPriority.builder().payloadPriorityId(dto.getPayloadPriorityId()).build());
        return payload;
    }
}