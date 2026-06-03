package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Screening;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateScreeningDTO {

    @NotNull(message = "O payload não pode ser nulo")
    private Long payloadId;

    @NotNull(message = "A altura medida não pode ser nula")
    @Positive(message = "A altura medida deve ser maior que zero")
    private Integer measuredHeight;

    @NotNull(message = "A largura medida não pode ser nula")
    @Positive(message = "A largura medida deve ser maior que zero")
    private Integer measuredWidth;

    @NotNull(message = "O comprimento medido não pode ser nulo")
    @Positive(message = "O comprimento medido deve ser maior que zero")
    private Integer measuredLength;

    @NotNull(message = "O peso medido não pode ser nulo")
    @Positive(message = "O peso medido deve ser maior que zero")
    private Integer measuredWeight;

    public static Screening toEntity(CreateScreeningDTO dto) {

        if (dto == null) {
            return null;
        }

        return Screening.builder()
                .payload(
                        Payload.builder()
                                .payloadId(dto.getPayloadId())
                                .build())
                .measuredHeight(dto.getMeasuredHeight())
                .measuredWidth(dto.getMeasuredWidth())
                .measuredLength(dto.getMeasuredLength())
                .measuredWeight(dto.getMeasuredWeight())
                .build();
    }

}
