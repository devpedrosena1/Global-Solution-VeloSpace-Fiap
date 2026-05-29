package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Shipper;

import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateShipperDTO {

    @NotBlank(message = "O tipo não pode estar em branco")
    @Pattern(regexp = "^(PF|PJ)$", message = "O tipo deve ser PF ou PJ")
    private String type;

    @Size(min = 11, max = 14, message = "O documento deve ter entre 11 e 14 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "O documento deve conter apenas números")
    private String shipperDocument;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    private String name;

    @NotBlank(message = "O documento não pode estar em branco")

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

    public static Shipper toEntity(CreateShipperDTO dto) {
        if (dto == null) {
            return null;
        }

        return Shipper.builder()
                .type(dto.getType())
                .shipperDocument(dto.getShipperDocument())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .hashedPassword(dto.getPassword())
                .build();
    }

}
