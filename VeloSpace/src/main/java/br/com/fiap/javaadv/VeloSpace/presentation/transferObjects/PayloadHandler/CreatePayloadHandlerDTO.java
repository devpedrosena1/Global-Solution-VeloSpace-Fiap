package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreatePayloadHandlerDTO {

    @NotNull(message = "O provedor de lançamento não pode ser nulo")
    private Long launchProviderId;

    @NotNull(message = "O CPF não pode ser nulo")
    @Digits(integer = 11, fraction = 0, message = "O CPF deve conter no máximo 11 dígitos")
    private Long cpf;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    private String name;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    private String email;

    @NotNull(message = "O telefone não pode ser nulo")
    @Digits(integer = 15, fraction = 0, message = "O telefone deve conter no máximo 15 dígitos")
    private Long phone;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
    private String password;

    public static PayloadHandler toEntity(CreatePayloadHandlerDTO dto) {

        if (dto == null) {
            return null;
        }

        return PayloadHandler.builder()
                .launchProvider(
                        LaunchProvider.builder().launchProviderId(dto.getLaunchProviderId()).build())
                .cpf(dto.getCpf())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .hashedPassword(dto.getPassword())
                .payloadHandlerStatus(
                        PayloadHandlerStatus.builder().payloadHandlerStatusId(1L).build())
                .build();
    }

}
